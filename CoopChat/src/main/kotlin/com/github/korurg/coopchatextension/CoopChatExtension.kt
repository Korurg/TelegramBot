package com.github.korurg.coopchatextension

import com.github.korurg.telegrambot.api.extension.TelegramBotExtension
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.Update

class CoopChatExtension : TelegramBotExtension {
    override fun getAuthor(): String {
        return "Korurg"
    }

    override fun getExtensionName(): String {
        return "CoopChatExtension"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun handleMessage(bot: Bot, update: Update, message: Message) {
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = "CoopChat Extension work!!!"
        )
    }
}
