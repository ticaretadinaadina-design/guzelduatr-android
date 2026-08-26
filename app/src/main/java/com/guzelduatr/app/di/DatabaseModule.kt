package com.guzelduatr.app.di

import android.content.Context
import androidx.room.Room
import com.guzelduatr.app.data.local.AppDatabase
import com.guzelduatr.app.data.local.DuaDao
import com.guzelduatr.app.data.local.ZikirDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "guzelduatr.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideZikirDao(db: AppDatabase): ZikirDao = db.zikirDao()

    @Provides
    fun provideDuaDao(db: AppDatabase): DuaDao = db.duaDao()
}
