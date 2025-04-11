package com.github.korurg.telegrambot.api.extension

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.Update

interface TelegramBotExtension {

    fun getAuthor(): String

    fun getExtensionName(): String

    fun getVersion(): String

    fun handleMessage(bot: Bot, update: Update, message: Message)
}
