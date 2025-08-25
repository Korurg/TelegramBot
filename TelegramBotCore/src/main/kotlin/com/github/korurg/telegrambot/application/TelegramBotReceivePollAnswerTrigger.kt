package com.github.korurg.telegrambot.application

import com.github.korurg.telegrambot.domain.TelegramPollAnswer

fun interface TelegramBotReceivePollAnswerTrigger {
    suspend fun handlePollAnswer(pollAnswer: TelegramPollAnswer)
}
