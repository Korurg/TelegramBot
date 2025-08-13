package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.ChatExternalId

data class TelegramStartPollCommand(
    val chatId: ChatExternalId,
    val question: String,
    val answers: List<String>,
    val isAnonymous: Boolean? = null,
)
