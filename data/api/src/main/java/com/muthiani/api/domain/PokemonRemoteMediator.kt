package com.muthiani.api.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.muthiani.api.domain.local.dao.PokeMonDao
import com.muthiani.api.domain.local.dao.RemoteKeysDao
import com.muthiani.api.domain.local.entities.Pokemon
import com.muthiani.api.domain.local.entities.RemoteKeys
import com.muthiani.api.domain.mappers.toToEntity
import com.muthiani.api.domain.remote.PokemonService
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokeMonDao: PokeMonDao,
    private val pokemonService: PokemonService,
    private val remoteKeysDao: RemoteKeysDao
) : RemoteMediator<Int, Pokemon>() {


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {



        val offset = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(20) ?: startOffset
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
        }


        try {
            val apiResponse = pokemonService.getPokemonList(offset = offset)
            val results = apiResponse.results


            val endOfPaginationReached = (apiResponse.next.getPage() ?: 0) == 120


            if (loadType == LoadType.REFRESH) {
                remoteKeysDao.clearRemoteKeys()
                pokeMonDao.clearPokemon()
            }

            val prevKey = if (offset == startOffset) null else offset - 20
            val nextKey = if (endOfPaginationReached) null else offset + 20


            val pokeMonList = results.map { it.toToEntity() }


            val remoteKeys = pokeMonList.map {
                RemoteKeys(
                    remoteKeyId = it.name.orEmpty(),
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }

            remoteKeysDao.insertAll(remoteKeys)
            pokeMonDao.insertPokemon(pokeMonList)
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }
    }

    companion object {
        const val startOffset = 0
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Pokemon>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { repoId ->
                remoteKeysDao.remoteKeysResultsId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Pokemon>): RemoteKeys? {

        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemon ->
                remoteKeysDao.remoteKeysResultsId(pokemon.name.orEmpty())
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Pokemon>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemon ->
                remoteKeysDao.remoteKeysResultsId(pokemon.name.orEmpty())
            }
    }
}

private fun String?.getPage(): Int? {
    val regex = Regex("""offset=(\d+)""")
    val matchResult = this?.let { regex.find(it) }
    return matchResult?.groupValues?.getOrNull(1)?.toIntOrNull()
}

