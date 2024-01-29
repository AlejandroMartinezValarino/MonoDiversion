package com.example.monodiversion.view

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.monodiversion.viewModel.GameViewModel

abstract class GameActivity : AppCompatActivity() {

    // Propiedad para almacenar el viewModel de la actividad
    protected lateinit var viewModel: GameViewModel

    // Método para inicializar el viewModel usando el factory
    protected fun initViewModel(factory: ViewModelProvider.Factory) {
        viewModel = ViewModelProvider(this, factory)[GameViewModel::class.java]
    }

    // Método para inflar el layout de la actividad
    protected abstract fun inflateLayout()

    // Método para iniciar el juego
    protected abstract fun startGame()

    // Método para pausar el juego
    protected abstract fun pauseGame()

    // Método para terminar el juego
    protected abstract fun endGame()
}