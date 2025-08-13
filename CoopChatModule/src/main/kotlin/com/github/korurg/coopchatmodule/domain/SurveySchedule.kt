package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.SurveyScheduleType
import com.github.korurg.coopchatmodule.domain.id.SurveyScheduleId
import java.time.LocalDate
import java.time.OffsetDateTime

data class SurveySchedule(
    val id: SurveyScheduleId? = null,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val type: SurveyScheduleType,
    val intervalCron: String,
    val skipUntil: LocalDate? = null,
    val timezone: String,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
