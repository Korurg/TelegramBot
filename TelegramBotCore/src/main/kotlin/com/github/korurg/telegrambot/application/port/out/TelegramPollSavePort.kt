package com.github.korurg.telegrambot.application.port.out

import com.github.korurg.telegrambot.domain.TelegramPoll
import com.github.korurg.telegrambot.domain.TelegramPollAnswer

interface TelegramPollSavePort {

    suspend fun savePoll(telegramPoll: TelegramPoll): TelegramPoll
    suspend fun savePollAnswer(pollAnswer: TelegramPollAnswer): TelegramPollAnswer
}
