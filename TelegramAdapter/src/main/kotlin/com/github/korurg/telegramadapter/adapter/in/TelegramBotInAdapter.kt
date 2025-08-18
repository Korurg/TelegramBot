package com.github.korurg.telegramadapter.adapter.`in`

import com.github.korurg.telegramadapter.mapper.toTelegramMessage
import com.github.korurg.telegramadapter.mapper.toTelegramPollAnswer
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.handlers.PollAnswerHandlerEnvironment
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException


class TelegramBotInAdapter(
    private val telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase
) {
    private val logger = KotlinLogging.logger {}

    internal fun receiveMessage(): MessageHandlerEnvironment.() -> Unit = {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                telegramMessageReceiveUseCase.receiveMessage(message.toTelegramMessage())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                logger.error(e) { e.message }
            }
        }
    }

    internal fun pollAnswer(): PollAnswerHandlerEnvironment.() -> Unit = {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                telegramMessageReceiveUseCase.receivePollAnswer(pollAnswer.toTelegramPollAnswer())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                logger.error(e) { e.message }
            }
        }
    }
}
