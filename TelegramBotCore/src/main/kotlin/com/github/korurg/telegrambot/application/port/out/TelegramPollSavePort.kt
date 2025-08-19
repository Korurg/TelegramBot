package com.github.korurg.telegrambot.application.port.out

import com.github.korurg.telegrambot.domain.TelegramPoll

interface TelegramPollSavePort {

    suspend fun savePoll(telegramPoll: TelegramPoll): TelegramPoll
}
