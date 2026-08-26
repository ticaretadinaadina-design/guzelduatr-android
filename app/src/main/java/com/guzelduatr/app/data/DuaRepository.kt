package com.guzelduatr.app.data

import com.guzelduatr.app.data.local.DuaDao
import com.guzelduatr.app.data.local.DuaEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DuaRepository @Inject constructor(
    private val dao: DuaDao
) {
    fun getAll(): Flow<List<DuaEntity>> = dao.getAll()

    fun search(q: String): Flow<List<DuaEntity>> = dao.search("%$q%")

    suspend fun insert(dua: DuaEntity) {
        dao.insert(dua)
    }

    suspend fun toggleFavorite(id: Long, fav: Boolean) {
        dao.updateFavorite(id, fav)
    }

    suspend fun seedIfEmpty(defaults: List<DuaEntity>) {
        val current = dao.getAll().first()
        if (current.isEmpty()) {
            defaults.forEach { dao.insert(it) }
        }
    }
}
