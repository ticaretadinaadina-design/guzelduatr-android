package com.guzelduatr.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ZikirEntity::class, DuaEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun zikirDao(): ZikirDao
    abstract fun duaDao(): DuaDao
}
