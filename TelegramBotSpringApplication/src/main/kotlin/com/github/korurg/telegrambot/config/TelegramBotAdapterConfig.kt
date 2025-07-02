package com.github.korurg.telegrambot.config

import com.github.korurg.telegrambot.adapter.`in`.TelegramBotInAdapter
import com.github.korurg.telegrambot.adapter.out.TelegramBotOutAdapter
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.domain.TelegramBot
import com.github.korurg.telegrambot.factory.TelegramBotFactory
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
    fun telegramBot(
        telegramBotConfig: TelegramBotConfig,
    ): TelegramBot {
        return TelegramBotFactory.telegramBot(
            telegramBotConfig
        )
    }

    @Bean
    fun telegramBotInAdapter(
        telegramBot: TelegramBot,
        telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase
    ): TelegramBotInAdapter {
        return TelegramBotFactory.telegramBotInAdapter(
            telegramBot = telegramBot,
            telegramMessageReceiveUseCase = telegramMessageReceiveUseCase
        )
    }

    @Bean
    fun telegramBotOutAdapter(
        telegramBot: TelegramBot
    ): TelegramBotOutAdapter {
        return TelegramBotFactory.telegramBotOutAdapter(
            telegramBot = telegramBot
        )
    }
}
