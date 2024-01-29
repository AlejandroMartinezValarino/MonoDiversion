package com.example.monodiversion.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.TypeConverters
import com.example.monodiversion.data.converter.Converter
import com.example.monodiversion.helper.BoxArrangement

@TypeConverters(Converter::class)
@Entity(
    tableName = "FLAG",
    primaryKeys = ["userId"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FlagEntity(
    @ColumnInfo(name = "userId")
    val userId: Long,
    @ColumnInfo(name = "colors")
    val colors: List<Int>,
    @ColumnInfo(name = "orientation")
    val orientation: BoxArrangement
)
