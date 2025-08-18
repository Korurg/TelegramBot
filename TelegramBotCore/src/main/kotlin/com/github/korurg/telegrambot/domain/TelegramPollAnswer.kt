package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.id.PollAnswerExternalId
import com.github.korurg.telegrambot.domain.id.PollExternalId

data class TelegramPollAnswer(
    val pollId: PollExternalId,
    val answersIds: List<PollAnswerExternalId>,
    val user: TelegramUser,
)
