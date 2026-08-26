package com.guzelduatr.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ZikirDao {
    @Query("SELECT * FROM zikir")
    fun getAll(): Flow<List<ZikirEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(zikir: ZikirEntity): Long

    @Update
    suspend fun update(zikir: ZikirEntity)

    @Query("UPDATE zikir SET sayac = :sayac WHERE id = :id")
    suspend fun updateSayac(id: Long, sayac: Int)

    @Query("SELECT * FROM zikir WHERE isim = :isim LIMIT 1")
    suspend fun findByName(isim: String): ZikirEntity?
}
