package com.muthiani.api.domain.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muthiani.api.domain.local.entities.Pokemon

@Dao
interface PokeMonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(data: List<Pokemon>)

    @Query("DELETE FROM pokemon")
    suspend fun clearPokemon()

    @Query("SELECT * FROM pokemon")
    fun getPokemon(): PagingSource<Int, Pokemon>
}