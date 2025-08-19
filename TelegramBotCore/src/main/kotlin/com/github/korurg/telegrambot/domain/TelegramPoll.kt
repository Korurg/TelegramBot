package com.github.korurg.telegrambot.domain

import com.github.korurg.telegrambot.domain.enums.PollType
import com.github.korurg.telegrambot.domain.id.ChatTelegramId
import com.github.korurg.telegrambot.domain.id.PollId
import com.github.korurg.telegrambot.domain.id.PollTelegramId
import java.time.OffsetDateTime

data class TelegramPoll(
    val id: PollId? = null,
    val telegramId: PollTelegramId,
    val chatTelegramId: ChatTelegramId,
    val question: String,
    val options: List<TelegramPollOption>,
    val isAnonymous: Boolean = false,
    val type: PollType = PollType.REGULAR,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
