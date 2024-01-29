package com.example.monodiversion.data.entity

import androidx.room.*

data class UserWithFlag(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val flag: FlagEntity?
)