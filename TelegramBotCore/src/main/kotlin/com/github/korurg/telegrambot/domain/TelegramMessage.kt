package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.MessageId


data class TelegramMessage(
    val messageId: MessageId,
    val chat: TelegramChat,
    val text: String?,
    val from: TelegramUser?
)
