package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.PollOptionExternalId
import com.github.korurg.telegrambot.domain.id.PollExternalId

data class TelegramPollAnswer(
    val pollId: PollExternalId,
    val answersIds: List<PollOptionExternalId>,
    val user: TelegramUser,
)
