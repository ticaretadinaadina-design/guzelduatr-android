package com.guzelduatr.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zikir")
data class ZikirEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val isim: String,
    val hedef: Int,
    val sayac: Int = 0
)
