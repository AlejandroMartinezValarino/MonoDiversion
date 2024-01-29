package com.example.monodiversion.helper

import android.content.Context

class Size {
    fun height(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return (displayMetrics.heightPixels*160)/context.resources.displayMetrics.densityDpi
    }
    fun width(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }
}
