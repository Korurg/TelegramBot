package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.UserId

data class TelegramUser(
    val userId: UserId,
    val isBot: Boolean,
    val firstName: String,
    val lastName: String?,
    val username: String?,
    val languageCode: String?
)
