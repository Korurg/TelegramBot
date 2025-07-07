package com.github.korurg.telegrambot.application

import com.github.korurg.telegrambot.domain.TelegramMessage

fun interface TelegramBotReceiveMessageTrigger {
    fun handleMessage(message: TelegramMessage)
}
