package com.example.monodiversion.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.monodiversion.data.entity.FlagEntity
import com.example.monodiversion.data.entity.UserEntity
import com.example.monodiversion.data.entity.UserWithFlag
import com.example.monodiversion.data.entity.UserWithScores
import com.example.monodiversion.model.User

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM USER")
    suspend fun getAll():List<UserWithFlag>

    @Upsert
    suspend fun upsert(user: UserEntity):Long

    @Transaction
    @Query("SELECT * FROM USER WHERE id = :userId")
    suspend fun getById(userId: Long): UserWithFlag?

    @Query("DELETE FROM USER WHERE id = :userId")
    suspend fun removeById(userId: Long)

    @Transaction
    @Query("SELECT * FROM USER")
    suspend fun getAllUsersWithScores(): List<UserWithScores>
}