package com.github.korurg.telegrambot.application

import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.application.port.out.TelegramChatSavePort
import com.github.korurg.telegrambot.application.port.out.TelegramUserSavePort
import com.github.korurg.telegrambot.domain.TelegramMessage
import io.github.oshai.kotlinlogging.KotlinLogging

class TelegramBotService(
    val messageTriggers: List<TelegramBotReceiveMessageTrigger> = ArrayList(),
    val telegramUserSavePort: TelegramUserSavePort,
    val telegramChatSavePort: TelegramChatSavePort,
) : TelegramMessageReceiveUseCase {
    private val logger = KotlinLogging.logger {}

    override suspend fun receiveMessage(message: TelegramMessage) {
        telegramChatSavePort.saveTelegramChat(message.chat)

        message.from?.let {
            telegramUserSavePort.saveTelegramUser(it)
        }

        messageTriggers.forEach {
            try {
                it.handleMessage(message)
            } catch (e: Exception) {
                logger.error(e) { e.message }
            }
        }
    }
}
