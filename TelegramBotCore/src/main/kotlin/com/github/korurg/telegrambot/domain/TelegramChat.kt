package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.ChatExternalId
import com.github.korurg.telegrambot.domain.id.ChatInternalId
import java.time.OffsetDateTime

data class TelegramChat(
    val id: ChatInternalId? = null,
    val telegramId: ChatExternalId,
    val title: String?,
    val type: String,
    val created: OffsetDateTime = OffsetDateTime.now(),
    val updated: OffsetDateTime = OffsetDateTime.now(),
)
