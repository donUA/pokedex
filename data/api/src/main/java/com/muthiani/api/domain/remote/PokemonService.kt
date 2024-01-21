package com.muthiani.api.domain.remote

import com.muthiani.api.domain.remote.dto.PokeMonResponse
import com.muthiani.api.domain.remote.dto.PokemonInfoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int
    ): PokeMonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): PokemonInfoDto
}