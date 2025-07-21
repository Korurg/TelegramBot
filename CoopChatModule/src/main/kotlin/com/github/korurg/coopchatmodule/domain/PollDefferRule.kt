package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.id.PollDefferRuleId
import com.github.korurg.coopchatmodule.domain.id.PollId
import com.github.korurg.telegrambot.domain.id.UserId
import java.sql.Date
import java.time.OffsetDateTime

data class PollDefferRule(
    val id: PollDefferRuleId?,
    val pollId: PollId,
    val startDate: Date,
    val endDate: Date,
    val defferBy: UserId,
    val updated: OffsetDateTime,
    val created: OffsetDateTime,
)
