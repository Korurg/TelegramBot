package com.github.korurg.telegrambot.application

import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.application.port.out.TelegramChatSavePort
import com.github.korurg.telegrambot.application.port.out.TelegramUserSavePort
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramPollAnswer
import io.github.oshai.kotlinlogging.KotlinLogging

class TelegramBotService(
    val messageTriggers: List<TelegramBotReceiveMessageTrigger> = ArrayList(),
    val pollAnswerTriggers: List<TelegramBotReceivePollAnswerTrigger> = ArrayList(),
    val telegramUserSavePort: TelegramUserSavePort,
    val telegramChatSavePort: TelegramChatSavePort,
    val telegramPollService: TelegramPollService,
) : TelegramMessageReceiveUseCase {
    private val logger = KotlinLogging.logger {}

    override suspend fun receiveMessage(message: TelegramMessage) {
        telegramChatSavePort.saveTelegramChat(message.chat)

        message.from?.let {
            telegramUserSavePort.saveTelegramUser(it)
        }

        messageTriggers.forEach {
            try {
                it.handleMessage(message)
            } catch (e: Exception) {
                logger.error(e) { e.message }
            }
        }
    }

    override suspend fun receivePollAnswer(pollAnswer: TelegramPollAnswer) {
        val savedPollAnswer = telegramPollService.savePollAnswer(pollAnswer)

        pollAnswerTriggers.forEach {
            try {
                it.handlePollAnswer(savedPollAnswer)
            } catch (e: Exception) {
                logger.error(e) { e.message }
            }
        }
    }

}
