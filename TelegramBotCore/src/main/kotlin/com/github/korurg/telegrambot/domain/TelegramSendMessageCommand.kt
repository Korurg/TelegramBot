package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.ChatId

data class TelegramSendMessageCommand(
    val chatId: ChatId,
    val text: String
)
