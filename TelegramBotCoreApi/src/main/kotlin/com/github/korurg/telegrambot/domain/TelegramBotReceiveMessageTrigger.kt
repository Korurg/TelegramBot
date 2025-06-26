package com.github.korurg.telegrambot.domain

fun interface TelegramBotReceiveMessageTrigger {
    fun handleMessage(message: TelegramMessage)
}
