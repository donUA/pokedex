package com.muthiani.api.domain.remote.dto

import com.google.gson.annotations.SerializedName


data class PokeMonResponse (
  @SerializedName("count")
  var count: Int? = null,
  @SerializedName("next")
  var next: String?= null,
  @SerializedName("previous")
  var previous: String?= null,
  @SerializedName("results")
  var results  : List<PokemonDto> = emptyList()
)