package com.github.korurg.telegrambot.config

import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambotadapter.TelegramBotAdapter
import com.github.korurg.telegrambotadapter.TelegramBotConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TelegramBotAdapterConfig {

    @Bean
    fun telegramBotConfig(
        @Value("\${telegram.bot.token}") token: String
    ): TelegramBotConfig {
        return TelegramBotConfig(
            botToken = token
        )
    }

    @Bean
    fun telegramBotAdapter(
        telegramBotConfig: TelegramBotConfig,
        telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase
    ): TelegramBotAdapter {
        return TelegramBotAdapter(
            telegramBotConfig = telegramBotConfig,
            telegramMessageReceiveUseCase = telegramMessageReceiveUseCase
        )
    }
}
