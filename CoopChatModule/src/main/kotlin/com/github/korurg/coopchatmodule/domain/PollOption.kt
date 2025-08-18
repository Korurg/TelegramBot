package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.AnswerType
import com.github.korurg.coopchatmodule.domain.id.GameId
import com.github.korurg.coopchatmodule.domain.id.PollOptionId
import com.github.korurg.telegrambot.domain.id.PollOptionExternalId
import java.time.OffsetDateTime

data class PollOption(
    val id: PollOptionId,
    val telegramId: PollOptionExternalId,
    val optionText: String,
    val type: AnswerType,
    val gameIdOption: GameId?,
    val timeOption: OffsetDateTime?,
)
