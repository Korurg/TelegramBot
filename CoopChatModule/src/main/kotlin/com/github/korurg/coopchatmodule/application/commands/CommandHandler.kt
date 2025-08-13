package com.github.korurg.coopchatmodule.application.commands

import com.github.korurg.coopchatmodule.domain.Command
import com.github.korurg.telegrambot.domain.TelegramMessage

interface CommandHandler {

    suspend fun handle(command: Command, message: TelegramMessage)

    fun getCommand(): String
}
