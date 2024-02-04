package com.example.monodiversion.model

import com.example.monodiversion.helper.State

class AgilityGame : Game() {
    // Propiedad para almacenar la posición del botón en el juego
    var buttonPosition: Pair<Int, Int> = Pair(0, 0)
    // Propiedad para almacenar el tiempo límite para pulsar el botón
    var timeLimit: Long = 0L

    // Método para iniciar el juego
    override fun start() {
        // Generar una posición aleatoria para el botón
        buttonPosition = Pair((0..100).random(), (0..100).random())
        // Iniciar el tiempo según el nivel
        time = 10L * level
        // Iniciar el tiempo límite según el nivel
        timeLimit = 5L - level / 2
    }

    // Método para pausar el juego
    override fun pause() {
        // Cambiar el estado del juego a pausado
        state = State.PAUSED
    }

    // Método para terminar el juego
    override fun end() {
        // Cambiar el estado del juego a terminado
        state = State.ENDED
    }

    // Método para actualizar el juego
    override fun update() {
        // Si el juego está activo
        if (state == State.ACTIVE) {
            // Reducir el tiempo en un segundo
            time--
            // Reducir el tiempo límite en un segundo
            timeLimit--
            // Si el tiempo se agota
            if (time == 0L) {
                // Terminar el juego
                end()
            }
            // Si el tiempo límite se agota
            if (timeLimit == 0L) {
                // Terminar el juego
                end()
            }
        }
    }

    // Método para pulsar el botón
    fun pressButton() {
        // Si el juego está activo y el tiempo límite no se ha agotado
        if (state == State.ACTIVE && timeLimit > 0) {
            // Aumentar los puntos según el tiempo límite restante
            points += timeLimit
            // Aumentar el nivel en uno
            level++
            // Iniciar el juego de nuevo
            start()
        }
    }
}