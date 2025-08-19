package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.PollOptionId
import com.github.korurg.telegrambot.domain.id.PollOptionTelegramId

data class TelegramPollOption(
    val id: PollOptionId,
    val telegramId: PollOptionTelegramId,
    val option: String
)
