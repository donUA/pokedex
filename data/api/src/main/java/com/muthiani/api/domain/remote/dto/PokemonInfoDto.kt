package com.muthiani.api.domain.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonInfoDto(
    val name: String? = null,
    val sprites: Sprites? = null,
    @SerializedName("stats") var stats: List<Stats> = emptyList()
)

data class Sprites(
    @SerializedName("back_default") var backDefault: String? = null,
    @SerializedName("back_female") var backFemale: String? = null,
    @SerializedName("back_shiny") var backShiny: String? = null,
    @SerializedName("back_shiny_female") var backShinyFemale: String? = null,
    @SerializedName("front_default") var frontDefault: String? = null,
    @SerializedName("front_female") var frontFemale: String? = null,
    @SerializedName("front_shiny") var frontShiny: String? = null,
    @SerializedName("front_shiny_female") var frontShinyFemale: String? = null
)

data class Stats(
    @SerializedName("base_stat") var baseStat: Int? = null,
    @SerializedName("effort") var effort: Int? = null,
    @SerializedName("stat") var stat: Stat? = Stat()
)

data class Stat(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)

