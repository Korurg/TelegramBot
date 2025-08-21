package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.UserTelegramId
import com.github.korurg.telegrambot.domain.id.UserId
import java.time.OffsetDateTime

data class TelegramUser(
    val id: UserId? = null,
    val telegramId: UserTelegramId,
    val isBot: Boolean,
    val firstName: String,
    val lastName: String?,
    val username: String?,
    val languageCode: String?,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
