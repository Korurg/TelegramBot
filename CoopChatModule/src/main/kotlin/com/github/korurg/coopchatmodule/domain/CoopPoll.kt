package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.CoopPollType
import com.github.korurg.telegrambot.domain.TelegramPoll
import com.github.korurg.coopchatmodule.domain.id.CoopPollId
import java.time.OffsetDateTime

data class CoopPoll(
    val id: CoopPollId,
    val poll: TelegramPoll,
    val options: List<CoopPollOption>,
    val type: CoopPollType,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
