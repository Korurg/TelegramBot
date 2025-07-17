package com.github.korurg.telegrambot.config

import com.github.korurg.coopchatmodule.application.CommandParserService
import com.github.korurg.coopchatmodule.application.GameService
import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.`in`.SteamAppProviderPort
import com.github.korurg.coopchatmodule.application.port.out.GameCommandPort
import com.github.korurg.coopchatmodule.application.triggers.CommandTrigger
import com.github.korurg.coopchatmodule.application.triggers.SteamLinkTrigger
import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoopChatModuleConfig {

    @Bean
    fun steamLinkTrigger(
        gameService: GameService
    ): TelegramBotReceiveMessageTrigger {
        return SteamLinkTrigger(
            gameService
        )
    }

    @Bean
    fun commandParserService(): CommandParserService {
        return CommandParserService()
    }

    @Bean
    fun gameService(
        gameCommandPort: GameCommandPort,
        steamAppProviderPort: SteamAppProviderPort,
        gameQueryPort: GameQueryPort,
        telegramMessageSendPort: TelegramMessageSendPort,
    ): GameService {
        return GameService(
            gameCommandPort = gameCommandPort,
            steamAppProviderPort = steamAppProviderPort,
            gameQueryPort = gameQueryPort,
            telegramMessageSendPort = telegramMessageSendPort,
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
