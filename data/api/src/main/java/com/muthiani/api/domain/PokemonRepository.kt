package com.muthiani.api.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muthiani.api.domain.local.dao.PokeMonDao
import com.muthiani.api.domain.local.dao.RemoteKeysDao
import com.muthiani.api.domain.local.entities.Pokemon
import com.muthiani.api.domain.remote.PokemonService
import com.muthiani.api.domain.remote.dto.PokemonInfoDto
import kotlinx.coroutines.flow.Flow

class PokemonRepository(
    private val pokeMonDao: PokeMonDao,
    private val api: PokemonService,
    private val remoteKeysDao: RemoteKeysDao
) {

    fun getPokeMonResultsStream(): Flow<PagingData<Pokemon>> {
        val pagingSourceFactory = { pokeMonDao.getPokemon() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = REMOTE_SERVICE_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = PokemonRemoteMediator(
                pokeMonDao = pokeMonDao,
                pokemonService = api,
                remoteKeysDao = remoteKeysDao
            ),
        ).flow
    }


    suspend fun getPokemonInfo(name: String): PokemonInfoDto {
        return api.getPokemonInfo(name)
    }

    companion object {
        const val REMOTE_SERVICE_PAGE_SIZE = 20
    }
}