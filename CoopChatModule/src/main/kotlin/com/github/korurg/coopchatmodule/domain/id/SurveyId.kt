package com.github.korurg.coopchatmodule.domain.id

import java.util.*


@JvmInline
value class SurveyId(val value: UUID) {
    companion object {
        fun random(): SurveyId = SurveyId(UUID.randomUUID())
    }
}
