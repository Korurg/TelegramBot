package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.PollOptionTelegramId
import com.github.korurg.telegrambot.domain.id.PollTelegramId

data class TelegramPollAnswer(
    val pollTelegramId: PollTelegramId,
    val pollOptionTelegramId: PollOptionTelegramId,
    val user: TelegramUser,
)
