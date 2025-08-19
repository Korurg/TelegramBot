package com.github.korurg.coopchatmodule.application.triggers

import com.github.korurg.coopchatmodule.application.CommandParserService
import com.github.korurg.coopchatmodule.application.commands.CommandHandler
import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import com.github.korurg.telegrambot.application.TelegramMessageService
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand
import io.github.oshai.kotlinlogging.KotlinLogging

class CommandTrigger(
    private val commandParserService: CommandParserService,
    private val commandHandlers: List<CommandHandler>,
    private val telegramMessageService: TelegramMessageService
) : TelegramBotReceiveMessageTrigger {
    private val logger = KotlinLogging.logger {}
    private val commandHandlersMap = commandHandlers.associateBy { it.getCommand() }

    override suspend fun handleMessage(message: TelegramMessage) {
        message.text?.let { messageText ->
            if (!messageText.startsWith("/")) return

            val command = commandParserService.parse(messageText)

            try {
                val handler = commandHandlersMap[command.command]
                if (handler != null) {
                    handler.handle(command, message)
                } else {
                    telegramMessageService.sendMessage(
                        TelegramSendMessageCommand(
                            chatId = message.chat.telegramId,
                            text = "Unknown command"
                        )
                    )
                }
            } catch (e: Exception) {
                logger.error(e) { e.message }
                e.message?.let { errorMessage ->
                    telegramMessageService.sendMessage(
                        TelegramSendMessageCommand(
                            chatId = message.chat.telegramId,
                            text = errorMessage
                        )
                    )
                }
            }
        }
    }
}
