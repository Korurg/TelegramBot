package com.github.korurg.telegrambot.config

import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import com.github.korurg.telegrambot.application.TelegramBotService
import com.github.korurg.telegrambot.application.port.out.TelegramUserSavePort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TelegramBotConfiguration {

    @Bean
    fun telegramBotService(
        messageTriggers: List<TelegramBotReceiveMessageTrigger>,
        telegramUserSavePort: TelegramUserSavePort,
    ): TelegramBotService {
        return TelegramBotService(
            messageTriggers = messageTriggers,
            telegramUserSavePort = telegramUserSavePort
        )
    }
}
