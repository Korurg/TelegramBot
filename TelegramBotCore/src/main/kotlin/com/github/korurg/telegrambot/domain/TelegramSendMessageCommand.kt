package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.ChatExternalId
import com.github.korurg.telegrambot.domain.id.MessageId

data class TelegramSendMessageCommand(
    val chatId: ChatExternalId,
    val text: String,
    val replyToMessageId: MessageId? = null,
    val htmlToTelegramFormat: Boolean = true,
)
