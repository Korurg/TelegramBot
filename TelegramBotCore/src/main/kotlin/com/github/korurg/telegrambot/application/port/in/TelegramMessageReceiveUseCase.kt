package com.github.korurg.telegrambot.application.port.`in`

import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramPollAnswer

interface TelegramMessageReceiveUseCase {
    suspend fun receiveMessage(message: TelegramMessage)
    suspend fun receivePollAnswer(pollAnswer: TelegramPollAnswer)
}
