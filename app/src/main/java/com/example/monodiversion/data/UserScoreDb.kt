package com.example.monodiversion.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.monodiversion.data.dao.FlagDao
import com.example.monodiversion.data.dao.ScoreDao
import com.example.monodiversion.data.dao.UserDao
import com.example.monodiversion.data.entity.FlagEntity
import com.example.monodiversion.data.entity.ScoreEntity
import com.example.monodiversion.data.entity.UserEntity

@Database(
    entities = [UserEntity::class,ScoreEntity::class,FlagEntity::class],
    version = 2
)
abstract class UserScoreDb:RoomDatabase() {
    abstract fun userDao():UserDao
    abstract fun scoreDao():ScoreDao
    abstract fun flagDao():FlagDao
}