package com.github.korurg.telegrambotadapter.adapter.out

import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand
import com.github.korurg.telegrambotadapter.adapter.TelegramBot

class TelegramBotOutAdapter(
    private val telegramBot: TelegramBot
): TelegramMessageSendPort  {

    private val bot = telegramBot.bot

    override fun sendMessage(message: TelegramSendMessageCommand) {
        bot.sendMessage(
            chatId = com.github.kotlintelegrambot.entities.ChatId.Companion.fromId(message.chatId.id),
            text = message.text
        )
    }
}
