package com.example.monodiversion.model

import com.example.monodiversion.helper.State

class MemoryGame : Game() {
    // Propiedad para almacenar los números que se muestran en el juego
    var numbers: List<Int> = listOf()
    // Propiedad para almacenar los números que el usuario ha seleccionado
    var selected: List<Int> = listOf()
    // Propiedad para almacenar si los números están ocultos o no
    var hidden: Boolean = false

    // Método para iniciar el juego
    override fun start() {
        // Generar una lista de números aleatorios entre 1 y 10
        numbers = (1..10).shuffled().take(level)
        // Mostrar los números durante unos segundos
        hidden = false
        // Iniciar el tiempo según el nivel
        time = 10L * level
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
            // Si el tiempo se agota
            if (time == 0L) {
                // Terminar el juego
                end()
            }
            // Si los números no están ocultos
            if (!hidden) {
                // Ocultar los números
                hidden = true
            }
        }
    }

    // Método para seleccionar un número
    fun select(number: Int) {
        // Si el juego está activo y los números están ocultos
        if (state == State.ACTIVE && hidden) {
            // Añadir el número a la lista de seleccionados
            selected = selected + number
            // Si la lista de seleccionados tiene el mismo tamaño que la lista de números
            if (selected.size == numbers.size) {
                // Comprobar si las listas son iguales
                if (selected == numbers) {
                    // Calcular el tiempo que ha tardado el usuario
                    val tiempoTardado = 10 * level - time
                    // Calcular los puntos que ha ganado el usuario
                    val puntosGanados = 100 - tiempoTardado
                    // Aumentar los puntos según los puntos ganados
                    points += puntosGanados
                    // Aumentar el nivel en uno
                    level++
                    // Iniciar el juego de nuevo
                    start()
                } else {
                    // Terminar el juego
                    end()
                }
            }
        }
    }
}
