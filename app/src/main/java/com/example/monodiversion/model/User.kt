package com.example.monodiversion.model

import android.os.Parcelable
import com.example.monodiversion.helper.BoxArrangement
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id: Long = 0L,
    var name: String = "",
    var country: Pair<String, String> = Pair("", ""),
    var flag: Flag? = null
): Parcelable
