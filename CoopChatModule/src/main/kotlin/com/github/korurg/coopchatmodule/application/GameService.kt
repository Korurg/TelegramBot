package com.github.korurg.coopchatmodule.application

import com.github.korurg.coopchatmodule.application.port.out.GameSavePort
import com.github.korurg.coopchatmodule.domain.Game

class GameService(
    private val gameSavePort: GameSavePort
) {

    fun addGame(game: Game) {
        gameSavePort.sageGame(game)
    }
}
