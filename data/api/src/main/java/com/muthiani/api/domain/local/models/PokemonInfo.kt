package com.muthiani.api.domain.local.models

data class PokemonInfo (
    val pokemonName: String? = null,
    val imageUrl: String?=null,
    val totalStats: Int? = null,
    val statList: List<StatContent> = emptyList()
)

data class StatContent(
    val statName: String? = null,
    val statValue: Int? = null
)