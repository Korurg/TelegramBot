package com.github.korurg.telegrambot.config

import com.github.korurg.coopchatmodule.application.CommandParserService
import com.github.korurg.coopchatmodule.application.GameService
import com.github.korurg.coopchatmodule.application.port.out.GameSavePort
import com.github.korurg.coopchatmodule.application.triggers.CommandTrigger
import com.github.korurg.coopchatmodule.application.triggers.HelloWorldTrigger
import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
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

    @Bean
    fun commandParserService(): CommandParserService {
        return CommandParserService()
    }

    @Bean
    fun gameService(
        gameSavePort: GameSavePort
    ): GameService {
        return GameService(
            gameSavePort
        )
    }

    @Bean
    fun plainTextGameTrigger(
        commandParserService: CommandParserService,
        telegramMessageSendPort: TelegramMessageSendPort,
        gameService: GameService,
    ): TelegramBotReceiveMessageTrigger {
        return CommandTrigger(
            commandParserService,
            telegramMessageSendPort,
            gameService
        )
    }
}
