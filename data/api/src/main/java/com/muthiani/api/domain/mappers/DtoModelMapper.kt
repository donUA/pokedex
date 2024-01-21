package com.muthiani.api.domain.mappers

import com.muthiani.api.domain.local.entities.Pokemon
import com.muthiani.api.domain.local.models.PokemonInfo
import com.muthiani.api.domain.local.models.StatContent
import com.muthiani.api.domain.remote.dto.PokemonDto
import com.muthiani.api.domain.remote.dto.PokemonInfoDto


fun PokemonDto.toToEntity(): Pokemon {
    return Pokemon(
        name = this.name,
        url = this.url
    )
}

fun PokemonInfoDto.toPokemonInfo(): PokemonInfo {
    val total = stats.sumOf { it.baseStat ?: 0 }
    return PokemonInfo(
        imageUrl = this.sprites?.frontDefault,
        pokemonName = this.name,
        totalStats = total,
        statList = this.stats.map { StatContent(statName = it.stat?.name, statValue = it.baseStat) }
    )
}
