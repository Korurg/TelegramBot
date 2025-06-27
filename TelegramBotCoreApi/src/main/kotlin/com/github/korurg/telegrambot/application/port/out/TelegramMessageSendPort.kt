package com.github.korurg.telegrambot.application.port.out

import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand

fun interface TelegramMessageSendPort {

    fun sendMessage(message: TelegramSendMessageCommand)
}
