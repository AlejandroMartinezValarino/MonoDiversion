package com.example.monodiversion.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.monodiversion.data.converter.Converter

@TypeConverters(Converter::class)
@Entity(tableName = "USER")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id:Long=0L,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("country")
    val country: Pair<String, String>
)