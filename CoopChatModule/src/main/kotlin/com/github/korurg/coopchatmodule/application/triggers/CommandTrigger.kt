package com.github.korurg.coopchatmodule.application.triggers

import com.github.korurg.coopchatmodule.application.CommandParserService
import com.github.korurg.coopchatmodule.application.GameService
import com.github.korurg.coopchatmodule.config.Commands
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand

class CommandTrigger(
    private val commandParserService: CommandParserService,
    private val telegramMessageSendPort: TelegramMessageSendPort,
    private val gameService: GameService
) : TelegramBotReceiveMessageTrigger {


    override fun handleMessage(message: TelegramMessage) {
        message.text?.let { messageText ->
            if (!messageText.startsWith("/")) return

            val command = commandParserService.parse(messageText)

            //TODO: rewrite
            when (command.command) {
                Commands.GAME_ADD -> gameService.addGame(
                    Game(
                        name = requireNotNull(command.argument) {"game name is required"},
                        maxPlayers = 4,
                        minPlayers = 4,
                    )
                )

                else -> telegramMessageSendPort.sendMessage(
                    TelegramSendMessageCommand(
                        chatId = message.chat.id,
                        text = "Unknown command"
                    )
                )
            }

        }
    }
}
