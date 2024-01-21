package com.muthiani.pokedex

import android.app.Application
import android.util.Log
import com.muthiani.api.di.ApiModule
import com.muthiani.api.domain.local.di.DatabaseModule
import com.muthiani.main.di.MainUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class BasePokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            Log.d("KOIN", "starting koin")
            androidContext(this@BasePokedexApp)
            androidLogger(Level.INFO)
            modules(
                DatabaseModule.databaseModule,
                ApiModule.apiModule,
                MainUiModule.mainUiModule
            )
        }
    }
}