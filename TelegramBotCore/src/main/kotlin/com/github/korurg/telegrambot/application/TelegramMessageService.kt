package com.github.korurg.telegrambot.application

import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand

class TelegramMessageService(
    private val telegramMessageSendPort: TelegramMessageSendPort
) {
    suspend fun sendMessage(sendMessageCommand: TelegramSendMessageCommand) {
        telegramMessageSendPort.sendMessage(sendMessageCommand)
    }
}
