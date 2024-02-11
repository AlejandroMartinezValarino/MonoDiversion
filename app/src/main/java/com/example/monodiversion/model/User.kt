package com.example.monodiversion.model

import android.os.Parcelable
import com.example.monodiversion.helper.BoxArrangement
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long = 0L,
    var name: String = "Kiran",
    var country: Pair<String, String> = Pair("Not", "Found"),
    var flag: Flag? = null
) : Parcelable {
    override fun toString(): String {
        return name
    }
}