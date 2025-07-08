package com.github.korurg.telegramadapter.adapter.`in`

import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramUser
import com.github.korurg.telegrambot.domain.id.ChatId
import com.github.korurg.telegrambot.domain.id.UserId
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import io.github.oshai.kotlinlogging.KotlinLogging


class TelegramBotInAdapter (
    private val telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase
) {
    private val logger = KotlinLogging.logger {}

    internal fun receiveMessage():
            MessageHandlerEnvironment.() -> Unit =
        {
            try {
                telegramMessageReceiveUseCase.receiveMessage(
                    TelegramMessage(
                        chat = TelegramChat(
                            id = ChatId(message.chat.id),
                            title = message.chat.title,
                            type = message.chat.type,
                        ),
                        text = message.text,
                        from = message.from?.let {
                            TelegramUser(
                                id = UserId(it.id),
                                firstName = it.firstName,
                                lastName = it.lastName,
                                username = it.username,
                                isBot = it.isBot,
                                languageCode = it.languageCode,
                            )
                        }
                    )
                )
            } catch (e: Throwable) {
                logger.error(e) { e.message }
            }
        }
}
