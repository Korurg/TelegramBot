package com.github.korurg.telegrambot.application

import com.github.korurg.telegrambot.domain.TelegramMessage

fun interface TelegramBotReceiveMessageTrigger {
    suspend fun handleMessage(message: TelegramMessage)
}
