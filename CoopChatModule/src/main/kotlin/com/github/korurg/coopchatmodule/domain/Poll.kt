package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.PollType
import com.github.korurg.telegrambot.domain.id.PollExternalId
import com.github.korurg.telegrambot.domain.id.PollInternalId
import java.time.OffsetDateTime

data class Poll(
    val id: PollInternalId? = null,
    val telegramId: PollExternalId,
    val type: PollType,
    val options: List<PollOption>,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
