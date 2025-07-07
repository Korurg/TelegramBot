package com.github.korurg.coopchatmodule.triggers

import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand

class HelloWorldTrigger(
    private val telegramMessageSendPort: TelegramMessageSendPort
) : TelegramBotReceiveMessageTrigger {
    override fun handleMessage(message: TelegramMessage) {
        message.text?.let {
            telegramMessageSendPort.sendMessage(
                TelegramSendMessageCommand(
                    chatId = message.chat.id,
                    text = "Hello world"
                )
            )
        }
    }
}
