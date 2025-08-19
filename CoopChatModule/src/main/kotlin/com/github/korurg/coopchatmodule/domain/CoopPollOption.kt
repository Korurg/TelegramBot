package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.AnswerType
import com.github.korurg.coopchatmodule.domain.id.CoopPollOptionId
import com.github.korurg.coopchatmodule.domain.id.GameId
import com.github.korurg.telegrambot.domain.TelegramPollOption
import java.time.OffsetDateTime

data class CoopPollOption(
    val id: CoopPollOptionId,
    val pollOption: TelegramPollOption,
    val type: AnswerType,
    val gameIdOption: GameId?,
    val timeOption: OffsetDateTime?,
)
