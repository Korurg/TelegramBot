package com.github.korurg.telegrambot.adapter.out

import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand
import com.github.korurg.telegrambot.domain.TelegramBot

class TelegramBotOutAdapter(
    private val telegramBot: TelegramBot
): TelegramMessageSendPort  {

    private val bot = telegramBot.bot

    override fun sendMessage(sendMessageCommand: TelegramSendMessageCommand) {
        bot.sendMessage(
            chatId = com.github.kotlintelegrambot.entities.ChatId.Companion.fromId(sendMessageCommand.chatId.id),
            text = sendMessageCommand.text
        )
    }
}
