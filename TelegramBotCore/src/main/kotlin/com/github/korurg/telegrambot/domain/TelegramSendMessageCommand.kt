package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.ChatId
import com.github.korurg.telegrambot.domain.id.MessageId

data class TelegramSendMessageCommand(
    val chatId: ChatId,
    val text: String,
    val replyToMessageId: MessageId? = null,
    val htmlToTelegramFormat: Boolean = true,
)
