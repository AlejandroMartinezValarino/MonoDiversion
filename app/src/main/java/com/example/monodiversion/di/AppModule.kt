package com.example.monodiversion.di

import android.app.Application
import androidx.room.Room
import com.example.monodiversion.data.UserScoreDb
import com.example.monodiversion.data.dao.ScoreDao
import com.example.monodiversion.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): UserScoreDb =
        Room.databaseBuilder(application, UserScoreDb::class.java, "users")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(database: UserScoreDb): UserDao = database.userDao()

    @Provides
    fun provideScoreDao(database: UserScoreDb): ScoreDao = database.scoreDao()
}