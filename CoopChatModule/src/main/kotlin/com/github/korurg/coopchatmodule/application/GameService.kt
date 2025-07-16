package com.github.korurg.coopchatmodule.application

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.`in`.SteamAppProviderPort
import com.github.korurg.coopchatmodule.application.port.out.GameCommandPort
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.SaveSteamAppCommand
import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand
import java.time.OffsetDateTime

class GameService(
    private val gameCommandPort: GameCommandPort,
    private val steamAppProviderPort: SteamAppProviderPort,
    private val gameQueryPort: GameQueryPort,
    private val telegramMessageSendPort: TelegramMessageSendPort,
) {

    suspend fun saveGame(game: Game) {
        gameCommandPort.saveGame(game)
    }

    suspend fun saveSteamApp(saveSteamAppCommand: SaveSteamAppCommand) {
        val existedGame = gameQueryPort.findGameBySteamId(saveSteamAppCommand.steamId)

        if (existedGame != null && existedGame.steamApp?.fillState == SteamAppFillState.FILLED) {
            saveSteamAppCommand.message?.let { message ->
                telegramMessageSendPort.sendMessage(
                    TelegramSendMessageCommand(
                        chatId = message.chat.id,
                        text = "Game \"${existedGame.name}\" already saved",
                        replyToMessageId = message.messageId,
                    )
                )
            }
            return
        }

        val steamApp = steamAppProviderPort.findSteamAppBySteamId(saveSteamAppCommand.steamId)
        val game = Game(
            id = null,
            name = steamApp!!.name,
            maxPlayers = 4,
            minPlayers = 4,
            watchPurchases = true,
            watchPriceChanges = true,
            watchForUpdates = true,
            steamApp = steamApp,
            updated = OffsetDateTime.now(),
            created = OffsetDateTime.now(),
        )

        val savedGame = gameCommandPort.saveGame(game)

        saveSteamAppCommand.message?.let { message ->
            telegramMessageSendPort.sendMessage(
                TelegramSendMessageCommand(
                    chatId = message.chat.id,
                    text = "Game \"${savedGame.name}\" saved\nDescription: ${savedGame.steamApp?.shortDescription}",
                    replyToMessageId = message.messageId,
                )
            )
        }
    }
}
