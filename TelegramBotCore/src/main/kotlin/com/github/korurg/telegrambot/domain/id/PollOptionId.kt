package com.github.korurg.telegrambot.domain.id

import java.util.*


@JvmInline
value class PollOptionId(val value: UUID) {
    companion object {
        fun random(): PollOptionId = PollOptionId(UUID.randomUUID())
    }
}
