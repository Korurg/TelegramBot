package com.github.korurg.coopchatmodule.application.triggers

import com.github.korurg.coopchatmodule.application.GameService
import com.github.korurg.coopchatmodule.domain.SaveSteamAppCommand
import com.github.korurg.coopchatmodule.domain.id.SteamAppId
import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import com.github.korurg.telegrambot.domain.TelegramMessage

class SteamLinkTrigger(
    private val gameService: GameService
) : TelegramBotReceiveMessageTrigger {

    override suspend fun handleMessage(message: TelegramMessage) {
        message.text?.let { messageText ->
            val steamLinkRegex = Regex("https://store\\.steampowered\\.com/app/(\\d+)/[a-zA-Z_]+")

            val matchResult = steamLinkRegex.find(messageText)

            if (matchResult == null) {
                return
            }

            val groupValues = matchResult.groupValues
            val steamAppId = groupValues[1].toLong()

            gameService.saveSteamApp(
                SaveSteamAppCommand(
                    message = message,
                    steamId = SteamAppId(steamAppId),
                )
            )
        }
    }
}
