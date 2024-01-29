package com.example.monodiversion.model

import com.example.monodiversion.data.dao.ScoreDao
import com.example.monodiversion.data.entity.ScoreEntity
import javax.inject.Inject

class ScoreRepository @Inject constructor(private val database: ScoreDao) {

    suspend fun upsertScore(score: Score, userId: Long) {
        val scoreEntity = score.toScoreEntity(userId)
        database.upsertScore(scoreEntity)
    }

    suspend fun deleteScore(score: Score, userId: Long) {
        val scoreEntity = score.toScoreEntity(userId)
        database.deleteScore(scoreEntity)
    }

    suspend fun getScoresByUser(userId: Long): List<Score> {
        return database.getScoresByUser(userId)
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
