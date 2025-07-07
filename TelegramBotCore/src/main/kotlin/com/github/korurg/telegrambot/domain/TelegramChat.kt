package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.ChatId

data class TelegramChat(
    val id: ChatId,
    val title: String?,
    val type: String,
)
