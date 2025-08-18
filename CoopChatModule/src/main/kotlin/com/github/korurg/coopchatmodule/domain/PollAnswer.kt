package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.AnswerType
import com.github.korurg.coopchatmodule.domain.id.GameId
import com.github.korurg.coopchatmodule.domain.id.PollAnswerId
import com.github.korurg.telegrambot.domain.id.PollAnswerExternalId
import java.time.OffsetDateTime

data class PollAnswer(
    val id: PollAnswerId?,
    val pollAnswerId: PollAnswerExternalId,
    val answer: String,
    val type: AnswerType,
    val gameIdAnswer: GameId?,
    val timeAnswer: OffsetDateTime?,
)
