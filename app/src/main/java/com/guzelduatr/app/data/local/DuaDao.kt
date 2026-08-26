package com.guzelduatr.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DuaDao {
    @Query("SELECT * FROM dua")
    fun getAll(): Flow<List<DuaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dua: DuaEntity): Long

    @Query("SELECT * FROM dua WHERE baslik LIKE :query OR icerik LIKE :query")
    fun search(query: String): Flow<List<DuaEntity>>

    @Query("UPDATE dua SET favori = :fav WHERE id = :id")
    suspend fun updateFavorite(id: Long, fav: Boolean)
}
