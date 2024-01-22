package com.muthiani.api.di

import com.muthiani.api.ConstantUtils
import com.muthiani.api.domain.PokemonRepository
import com.muthiani.api.domain.remote.PokemonService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiModule {
    val apiModule = module {

        single {
            OkHttpClient.Builder().apply {
                readTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)
                addInterceptor(get<HttpLoggingInterceptor>())
            }.build()
        }

        single {
            Retrofit.Builder()
                /* Due to a conflict with koin dependency, project BuildConfig is not being generated.
                Ideally, the base url should be fetched from "BuildConfig.BASE_URL" as declared on gradle files
                With more time, I'd look into this conflict and resolve
                 */
                .baseUrl(ConstantUtils.BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single {
            HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            }
        }

        single<PokemonService> {
            val retrofit = get<Retrofit>()
            retrofit.create(PokemonService::class.java)
        }

        single {
            PokemonRepository(
                pokeMonDao = get(),
                api = get(),
                remoteKeysDao = get()
            )
        }
    }
}