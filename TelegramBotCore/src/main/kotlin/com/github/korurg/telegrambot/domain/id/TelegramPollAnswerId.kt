package com.github.korurg.telegrambot.domain.id

import java.util.*


@JvmInline
value class TelegramPollAnswerId(val value: UUID) {
    companion object {
        fun random(): TelegramPollAnswerId = TelegramPollAnswerId(UUID.randomUUID())
    }
}
