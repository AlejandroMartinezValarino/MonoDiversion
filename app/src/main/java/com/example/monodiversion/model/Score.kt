package com.example.monodiversion.model

import android.os.Parcelable
import com.example.monodiversion.helper.GameType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    var scoreId: Long = 0L,
    var points: Long = 0L,
    var gameType: GameType = GameType.AGILITY
): Parcelable
