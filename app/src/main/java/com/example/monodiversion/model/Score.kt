package com.example.monodiversion.model

import android.os.Parcelable
import com.example.monodiversion.helper.GameType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    val scoreId: Long,
    val points: Long,
    val gameType: GameType
): Parcelable
