package com.github.korurg.telegramadapter.adapter.out

import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegramadapter.domain.TelegramBot
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand
import com.github.kotlintelegrambot.entities.ChatId
import org.springframework.stereotype.Component

@Component
class TelegramBotOutAdapter(
    private val telegramBot: TelegramBot
) : TelegramMessageSendPort {

    private val bot = telegramBot.bot

    override fun sendMessage(sendMessageCommand: TelegramSendMessageCommand) {
        bot.sendMessage(
            chatId = ChatId.Companion.fromId(sendMessageCommand.chatId.value),
            text = sendMessageCommand.text
        )
    }
}
