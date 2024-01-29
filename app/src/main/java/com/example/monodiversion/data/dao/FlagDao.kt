package com.example.monodiversion.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.monodiversion.data.entity.FlagEntity

@Dao
interface FlagDao {

    @Upsert
    suspend fun insert(flag: FlagEntity)

    @Query("SELECT * FROM FLAG WHERE userId = :userId")
    suspend fun getByUserId(userId: Long): FlagEntity?

    @Query("DELETE FROM FLAG WHERE userId = :userId")
    suspend fun removeByUserId(userId: Long)
}
