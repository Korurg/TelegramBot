package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.PollOptionTelegramId
import com.github.korurg.telegrambot.domain.id.PollTelegramId

data class TelegramPollAnswer(
    val pollId: PollTelegramId,
    val answersIds: List<PollOptionTelegramId>,
    val user: TelegramUser,
)
