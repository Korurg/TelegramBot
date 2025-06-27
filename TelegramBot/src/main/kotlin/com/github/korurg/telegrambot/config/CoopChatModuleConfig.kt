package com.github.korurg.telegrambot.config

import com.github.korurg.coopchatmodule.triggers.HelloWorldTrigger
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramBotReceiveMessageTrigger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoopChatModuleConfig {

    @Bean
    fun helloWorldTrigger(
        telegramMessageSendPort: TelegramMessageSendPort
    ): TelegramBotReceiveMessageTrigger {
        return HelloWorldTrigger(
            telegramMessageSendPort
        )
    }
}
