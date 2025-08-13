package com.github.korurg.coopchatmodule.application.commands

import com.github.korurg.coopchatmodule.application.GameService
import com.github.korurg.coopchatmodule.config.Commands
import com.github.korurg.coopchatmodule.domain.Command
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.enums.GameState
import com.github.korurg.telegrambot.domain.TelegramMessage
import java.time.OffsetDateTime

class GameAddCommandHandler(
    private val gameService: GameService
) : CommandHandler {
    override suspend fun handle(command: Command, message: TelegramMessage) {
        gameService.saveGame(
            Game(
                name = requireNotNull(command.argument) { "game name is required" },
                maxPlayers = 4,
                minPlayers = 4,
                watchPurchases = true,
                watchPriceChanges = true,
                watchForUpdates = true,
                updated = OffsetDateTime.now(),
                created = OffsetDateTime.now(),
                gameState = GameState.NEW
            )
        )
    }

    override fun getCommand(): String {
        return Commands.GAME_ADD
    }
}
