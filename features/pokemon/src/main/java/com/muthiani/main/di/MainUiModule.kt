package com.muthiani.main.di

import com.muthiani.main.ui.landing.PokeMonViewModel
import com.muthiani.main.ui.pokemonInfo.PokemonInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainUiModule {
    val mainUiModule = module {
        viewModel {
            PokeMonViewModel(
                repository = get()
            )
        }

        viewModel {
            PokemonInfoViewModel(
                repository = get()
            )
        }
    }
}