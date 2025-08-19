package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.ChatTelegramId
import com.github.korurg.telegrambot.domain.id.MessageId

data class TelegramSendMessageCommand(
    val chatId: ChatTelegramId,
    val text: String,
    val replyToMessageId: MessageId? = null,
    val htmlToTelegramFormat: Boolean = true,
)
