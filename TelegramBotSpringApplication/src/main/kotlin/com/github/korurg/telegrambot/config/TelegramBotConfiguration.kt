package com.github.korurg.telegrambot.config

import com.github.korurg.telegrambot.application.TelegramBotService
import com.github.korurg.telegrambot.domain.TelegramBotReceiveMessageTrigger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TelegramBotConfiguration {

    @Bean
    fun telegramBotService(
        messageTriggers: List<TelegramBotReceiveMessageTrigger>
    ): TelegramBotService {
        return TelegramBotService(
            messageTriggers = messageTriggers
        )
    }
}
