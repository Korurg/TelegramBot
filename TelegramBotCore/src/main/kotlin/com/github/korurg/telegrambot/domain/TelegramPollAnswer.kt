package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.PollOptionTelegramId
import com.github.korurg.telegrambot.domain.id.PollTelegramId
import com.github.korurg.telegrambot.domain.id.TelegramPollAnswerId

data class TelegramPollAnswer(
    val id: TelegramPollAnswerId,
    val pollTelegramId: PollTelegramId,
    val pollOptionTelegramId: PollOptionTelegramId,
    val user: TelegramUser,
)
