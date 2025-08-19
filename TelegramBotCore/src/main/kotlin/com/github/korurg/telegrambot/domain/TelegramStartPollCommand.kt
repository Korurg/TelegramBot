package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.enums.PollType
import com.github.korurg.telegrambot.domain.id.ChatTelegramId

data class TelegramStartPollCommand(
    val chatTelegramId: ChatTelegramId,
    val question: String,
    val options: List<String>,
    val isAnonymous: Boolean = false,
    val type: PollType = PollType.REGULAR,
)
