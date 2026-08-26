package com.guzelduatr.app.data

import com.guzelduatr.app.data.local.ZikirDao
import com.guzelduatr.app.data.local.ZikirEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ZikirRepository @Inject constructor(
    private val dao: ZikirDao
) {
    fun getAllZikir(): Flow<List<ZikirEntity>> = dao.getAll()

    suspend fun seedIfEmpty(defaults: List<ZikirEntity>) {
        val current = dao.getAll().first()
        if (current.isEmpty()) {
            defaults.forEach { dao.insert(it) }
        }
    }

    suspend fun increment(id: Long) {
        val list = dao.getAll().first()
        val item = list.find { it.id == id } ?: return
        dao.updateSayac(id, item.sayac + 1)
    }

    suspend fun decrement(id: Long) {
        val list = dao.getAll().first()
        val item = list.find { it.id == id } ?: return
        val newVal = if (item.sayac > 0) item.sayac - 1 else 0
        dao.updateSayac(id, newVal)
    }

    suspend fun reset(id: Long) {
        dao.updateSayac(id, 0)
    }
}
