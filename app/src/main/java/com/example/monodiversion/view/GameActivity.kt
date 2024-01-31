package com.example.monodiversion.view

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

abstract class GameActivity : AppCompatActivity() {

    // Método para inflar el layout de la actividad
    protected abstract fun inflateLayout()

    // Método para iniciar el juego
    protected abstract fun startGame()

    // Método para pausar el juego
    protected abstract fun pauseGame()

    // Método para terminar el juego
    protected abstract fun endGame()
}