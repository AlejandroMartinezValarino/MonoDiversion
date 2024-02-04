package com.example.monodiversion.data.dao

import androidx.room.*
import com.example.monodiversion.data.entity.ScoreEntity
import com.example.monodiversion.helper.GameType
import com.example.monodiversion.model.Score

@Dao
interface ScoreDao {
    @Upsert
    suspend fun upsertScore(score: ScoreEntity)

    @Delete
    suspend fun deleteScore(score: ScoreEntity)

    @Query("SELECT * FROM SCORE WHERE userId = :userId")
    suspend fun getScoresByUser(userId: Long): List<ScoreEntity>

    @Query("SELECT * FROM SCORE WHERE userId= :userId AND gameType= :gameType")
    suspend fun getScoresByUserAndGameType(userId: Long,gameType: GameType):List<ScoreEntity>
}