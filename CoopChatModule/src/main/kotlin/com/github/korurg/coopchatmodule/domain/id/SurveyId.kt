package com.github.korurg.coopchatmodule.domain.id


@JvmInline
value class SurveyId(val value: Long) {
    fun defaultPoolId(): Long = 0
}
