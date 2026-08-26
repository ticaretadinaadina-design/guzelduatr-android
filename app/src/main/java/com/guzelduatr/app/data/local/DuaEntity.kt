package com.guzelduatr.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dua")
data class DuaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val baslik: String,
    val kategori: String,
    val icerik: String,
    val favori: Boolean = false
)
