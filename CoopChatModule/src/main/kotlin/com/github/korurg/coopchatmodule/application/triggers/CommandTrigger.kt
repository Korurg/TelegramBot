package com.github.korurg.coopchatmodule.application.triggers

import com.github.korurg.coopchatmodule.application.CommandParserService
import com.github.korurg.coopchatmodule.application.GameService
import com.github.korurg.coopchatmodule.config.Commands
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.OffsetDateTime

class CommandTrigger(
    private val commandParserService: CommandParserService,
    private val telegramMessageSendPort: TelegramMessageSendPort,
    private val gameService: GameService
) : TelegramBotReceiveMessageTrigger {
    private val logger = KotlinLogging.logger {}

    override suspend fun handleMessage(message: TelegramMessage) {
        message.text?.let { messageText ->
            if (!messageText.startsWith("/")) return

            val command = commandParserService.parse(messageText)

            //TODO: rewrite
            try {
                when (command.command) {
                    Commands.GAME_ADD -> gameService.saveGame(
                        Game(
                            name = requireNotNull(command.argument) { "game name is required" },
                            maxPlayers = 4,
                            minPlayers = 4,
                            watchPurchases = true,
                            watchPriceChanges = true,
                            watchForUpdates = true,
                            updated = OffsetDateTime.now(),
                            created = OffsetDateTime.now(),
                        )
                    )

                    else -> telegramMessageSendPort.sendMessage(
                        TelegramSendMessageCommand(
                            chatId = message.chat.id,
                            text = "Unknown command"
                        )
                    )
                }
            } catch (e: Exception) {
                logger.error(e) { e.message }
                e.message?.let { errorMessage ->
                    telegramMessageSendPort.sendMessage(
                        TelegramSendMessageCommand(
                            chatId = message.chat.id,
                            text = errorMessage
                        )
                    )
                }
            }
        }
    }
}
