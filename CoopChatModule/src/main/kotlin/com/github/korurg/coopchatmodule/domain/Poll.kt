package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.PollType
import com.github.korurg.telegrambot.domain.id.PollExternalId
import com.github.korurg.telegrambot.domain.id.PollInternalId
import java.time.OffsetDateTime

data class Poll(
    val id: PollInternalId?,
    val telegramId: PollExternalId,
    val type: PollType,
    val answers: List<PollAnswer>,
    val updated: OffsetDateTime,
    val created: OffsetDateTime,
)
