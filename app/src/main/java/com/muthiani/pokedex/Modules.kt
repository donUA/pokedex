package com.muthiani.pokedex

import com.muthiani.api.di.ApiModule
import com.muthiani.api.domain.local.di.DatabaseModule
import com.muthiani.main.di.MainUiModule
import org.koin.core.context.loadKoinModules

fun injectFeature() = loadFeatures

private val loadFeatures by lazy {
    loadKoinModules(
        listOf(
            DatabaseModule.databaseModule,
            ApiModule.apiModule,
            MainUiModule.mainUiModule
        )
    )
}