package com.example.monodiversion.model

import android.os.Parcelable
import com.example.monodiversion.helper.BoxArrangement
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flag(
    var colors: List<Int>,
    var orientation: BoxArrangement
):Parcelable {
    override fun toString(): String {
        return "Flag(colors=$colors, orientation=$orientation)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Flag) return false
        if (colors != other.colors) return false
        return orientation == other.orientation
    }

    override fun hashCode(): Int {
        var result = colors.hashCode()
        result = 31 * result + orientation.hashCode()
        return result
    }
}
