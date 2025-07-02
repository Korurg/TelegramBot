package com.github.korurg.telegrambot.domain


data class TelegramMessage(
    val chat: TelegramChat,
    val text: String?
)
