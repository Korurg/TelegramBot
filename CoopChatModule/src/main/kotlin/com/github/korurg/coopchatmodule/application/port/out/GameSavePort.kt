package com.github.korurg.coopchatmodule.application.port.out

import com.github.korurg.coopchatmodule.domain.Game

fun interface GameSavePort {
    fun sageGame(game: Game)
}
