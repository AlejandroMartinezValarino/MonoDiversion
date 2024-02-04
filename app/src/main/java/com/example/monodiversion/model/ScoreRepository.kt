package com.example.monodiversion.model

import com.example.monodiversion.data.UserScoreDb
import com.example.monodiversion.data.dao.ScoreDao
import com.example.monodiversion.data.entity.ScoreEntity
import com.example.monodiversion.helper.GameType
import javax.inject.Inject

class ScoreRepository @Inject constructor(private val database: UserScoreDb) {

    suspend fun upsertScore(score: Score, userId: Long) {
        val scoreEntity = score.toScoreEntity(userId)
        database.scoreDao().upsertScore(scoreEntity)
    }

    suspend fun deleteScore(score: Score, userId: Long) {
        val scoreEntity = score.toScoreEntity(userId)
        database.scoreDao().deleteScore(scoreEntity)
    }

    suspend fun getScoresByUser(userId: Long): List<Score> {
        return database.scoreDao().getScoresByUser(userId).toScores()
    }

    suspend fun getScoresByUserAndGameType(userId: Long,gameType: GameType):List<Score>{
        return database.scoreDao().getScoresByUserAndGameType(userId,gameType).toScores()
    }

    //Mappers
    private fun ScoreEntity.toScore(): Score =
        Score(
            scoreId = this.scoreId,
            points = this.points,
            gameType = this.gameType
        )

    private fun Score.toScoreEntity(userId: Long) : ScoreEntity =
        ScoreEntity(
            scoreId = this.scoreId,
            userId = userId,
            gameType = this.gameType,
            points = this.points
        )

    private fun List<ScoreEntity>.toScores(): List<Score> =
        this.map { it.toScore() }
}
