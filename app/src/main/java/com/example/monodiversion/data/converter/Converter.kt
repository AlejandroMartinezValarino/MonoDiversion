package com.example.monodiversion.data.converter

import com.example.monodiversion.helper.BoxArrangement
import androidx.room.TypeConverter
import com.example.monodiversion.helper.GameType

class Converter {

    @TypeConverter
    fun fromPair(pair: Pair<String, String>): String {
        return pair.first + "," + pair.second
    }

    @TypeConverter
    fun toPair(data: String): Pair<String, String> {
        val pieces = data.split(",")
        return Pair(pieces[0], pieces[1])
    }
    @TypeConverter
    fun fromIntListToString(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringToIntList(string: String): List<Int> {
        return string.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromBoxArrangementToString(boxArrangement: BoxArrangement): String {
        return boxArrangement.name
    }

    @TypeConverter
    fun fromStringToBoxArrangement(string: String): BoxArrangement {
        return BoxArrangement.valueOf(string)
    }

    @TypeConverter
    fun fromGameTypeToString(gameType: GameType): String {
        return gameType.name
    }

    @TypeConverter
    fun fromStringToGameType(string: String): GameType {
        return GameType.valueOf(string)
    }
}