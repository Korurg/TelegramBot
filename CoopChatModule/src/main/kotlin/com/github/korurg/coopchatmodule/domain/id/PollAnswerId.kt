package com.github.korurg.coopchatmodule.domain.id

import java.util.*


@JvmInline
value class PollAnswerId(val value: UUID) {

    companion object {
        fun random(): PollAnswerId = PollAnswerId(UUID.randomUUID())
    }
}
