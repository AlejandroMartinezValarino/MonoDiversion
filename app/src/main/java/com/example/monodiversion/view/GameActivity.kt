package com.example.monodiversion.view

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

abstract class GameActivity : AppCompatActivity() {
    protected abstract fun inflateLayout()
    protected abstract fun startGame()
    protected abstract fun pauseGame()
    protected abstract fun endGame()
}