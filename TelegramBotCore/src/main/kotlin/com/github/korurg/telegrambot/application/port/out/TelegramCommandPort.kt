package com.github.korurg.telegrambot.application.port.out

import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.id.PollTelegramId

interface TelegramCommandPort {
    suspend fun startPoll(startPollCommand: TelegramStartPollCommand): PollTelegramId
}
