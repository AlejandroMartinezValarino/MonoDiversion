package com.example.monodiversion.model

import com.example.monodiversion.helper.State

abstract class Game {

    @JvmField
    var level: Int = 0

    var time: Long = 0L

    @JvmField
    var points: Long = 0L

    @JvmField
    var state: State = State.ACTIVE

    abstract fun start()

    abstract fun pause()

    abstract fun end()

    abstract fun update()
}