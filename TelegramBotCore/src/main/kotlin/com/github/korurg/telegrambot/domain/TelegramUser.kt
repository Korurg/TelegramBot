package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.UserExternalId
import com.github.korurg.telegrambot.domain.id.UserInternalId
import java.time.OffsetDateTime

data class TelegramUser(
    val id: UserInternalId? = null,
    val telegramId: UserExternalId,
    val isBot: Boolean,
    val firstName: String,
    val lastName: String?,
    val username: String?,
    val languageCode: String?,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
