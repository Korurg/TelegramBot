package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.id.PollId
import java.sql.Date
import java.time.OffsetDateTime
import java.time.OffsetTime

data class Poll(
    val id: PollId?,
    val isActive: Boolean,
    val dateStart: Date,
    val dateEnd: Date?,
    val timeStart: OffsetTime,
    val timeEnd: OffsetTime,
    val updated: OffsetDateTime,
    val created: OffsetDateTime,
)
