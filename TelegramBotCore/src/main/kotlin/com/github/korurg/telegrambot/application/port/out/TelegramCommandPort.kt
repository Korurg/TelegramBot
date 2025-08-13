package com.github.korurg.telegrambot.application.port.out

import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.id.PollExternalId

interface TelegramCommandPort {


    suspend fun startPoll(startPollCommand: TelegramStartPollCommand): PollExternalId
}
