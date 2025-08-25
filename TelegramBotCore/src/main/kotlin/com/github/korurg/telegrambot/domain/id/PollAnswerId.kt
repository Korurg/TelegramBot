package com.github.korurg.telegrambot.domain.id

import java.util.*


@JvmInline
value class PollAnswerId(val value: UUID) {
    companion object {
        fun random() = PollAnswerId(UUID.randomUUID())
    }
}
