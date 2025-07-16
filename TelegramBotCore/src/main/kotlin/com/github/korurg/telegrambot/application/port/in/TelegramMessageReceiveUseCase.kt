package com.github.korurg.telegrambot.application.port.`in`

import com.github.korurg.telegrambot.domain.TelegramMessage

fun interface TelegramMessageReceiveUseCase {
    suspend fun receiveMessage(message: TelegramMessage)
}
