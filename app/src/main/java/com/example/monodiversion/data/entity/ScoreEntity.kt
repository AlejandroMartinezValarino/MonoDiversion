package com.example.monodiversion.data.entity

import androidx.room.*
import com.example.monodiversion.helper.GameType
import com.example.monodiversion.model.User

@Entity(
    tableName = "SCORE",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "scoreId")
    val scoreId: Long,
    @ColumnInfo(name = "userId")
    val userId: Long,
    @ColumnInfo(name = "gameType")
    val gameType: GameType,
    @ColumnInfo(name = "points")
    val points: Long
)
