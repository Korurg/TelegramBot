package com.github.korurg.coopchatmodule.domain.id


@JvmInline
value class PollId(val value: Long) {
    fun defaultPoolId(): Long = 0
}
