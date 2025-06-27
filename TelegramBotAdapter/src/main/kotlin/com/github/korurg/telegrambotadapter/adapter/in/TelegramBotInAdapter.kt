package com.github.korurg.telegrambotadapter.adapter.`in`

import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.id.ChatId

class TelegramBotInAdapter(
    private val telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase
) {

    internal fun receiveMessage():
            com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment.() -> Unit =
        {
            telegramMessageReceiveUseCase.receiveMessage(
                TelegramMessage(
                    chat = TelegramChat(
                        chatId = ChatId(message.chat.id)
                    ),
                    text = message.text
                )
            )
        }
}
