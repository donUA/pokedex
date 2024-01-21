package com.muthiani.api.domain.local.di

import com.muthiani.api.domain.local.PokemonDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DatabaseModule {
    val databaseModule = module {
        single {
            PokemonDatabase.getDatabase(androidContext())
        }

        single {
            val database = get<PokemonDatabase>()
            database.pokemonDao()
        }

        single {
            val database = get<PokemonDatabase>()
            database.remoteKeysDao()
        }
    }
}