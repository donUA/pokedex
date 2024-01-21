package com.muthiani.api.domain.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muthiani.api.ConstantUtils
import com.muthiani.api.domain.local.dao.PokeMonDao
import com.muthiani.api.domain.local.dao.RemoteKeysDao
import com.muthiani.api.domain.local.entities.Pokemon
import com.muthiani.api.domain.local.entities.RemoteKeys


@Database(entities = [Pokemon::class, RemoteKeys::class], version = 4)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokeMonDao

    abstract fun remoteKeysDao(): RemoteKeysDao


    companion object {
        private const val DB_NAME = ConstantUtils.DB_NAME

        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}