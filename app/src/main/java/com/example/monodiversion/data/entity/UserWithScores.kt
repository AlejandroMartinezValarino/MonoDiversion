package com.example.monodiversion.data.entity

import androidx.room.*

data class UserWithScores(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val scores: List<ScoreEntity>
)
