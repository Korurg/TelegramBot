package com.github.korurg.telegrambot.service

import com.github.korurg.telegrambot.TelegramBot
import com.github.korurg.telegrambot.api.config.TelegramBotConfig
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TelegramBotService(
    @Value("\${telegram.bot.token}")
    val telegramBotToken: String
) {

    private val telegramBot: TelegramBot = TelegramBot(TelegramBotConfig(botToken = telegramBotToken))

    @PostConstruct
    fun init() {
        telegramBot.start()
    }
}
