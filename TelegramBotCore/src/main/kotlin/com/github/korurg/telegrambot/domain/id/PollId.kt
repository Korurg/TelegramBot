package com.github.korurg.telegrambot.domain.id

import java.util.*


@JvmInline
value class PollId(val value: UUID) {
    companion object {
        fun random(): PollId = PollId(UUID.randomUUID())
    }
}
