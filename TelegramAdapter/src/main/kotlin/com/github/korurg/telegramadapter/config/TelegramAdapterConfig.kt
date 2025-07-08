package com.github.korurg.telegramadapter.config

import com.github.korurg.coopchatmodule.config.Commands
import com.github.korurg.telegramadapter.adapter.`in`.TelegramBotInAdapter
import com.github.korurg.telegramadapter.domain.TelegramBot
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.BotCommand
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.github.korurg.telegramadapter")
class TelegramAdapterConfig {

    @Bean
    fun telegramBotInAdapter(
        telegramBot: TelegramBot,
        telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase
    ): TelegramBotInAdapter {
        val telegramBotInAdapter = TelegramBotInAdapter(telegramMessageReceiveUseCase)

        telegramBot.messages.add {
            telegramBotInAdapter.receiveMessage().invoke(this)
        }

        return telegramBotInAdapter
    }


    @Bean
    fun telegramBot(
        telegramBotConfig: TelegramBotConfig,
    ): TelegramBot {

        val messages: MutableList<suspend MessageHandlerEnvironment.() -> Unit> =
            ArrayList()

        val callback: Dispatcher.() -> Unit = {
            message {
                messages.forEach {
                    it.invoke(this)
                }
            }
        }

        val bot = bot {
            token = telegramBotConfig.botToken
            dispatch(callback)
        }

        bot.setMyCommands(Commands.COMMANDS.map { commandInfo ->
            BotCommand(
                command = commandInfo.command,
                description = (commandInfo.description ?: "") + (commandInfo.example?.let { " Example: $it" } ?: "")
            )
        })

        bot.startPolling()
        return TelegramBot(
            bot = bot,
            callback = callback,
            messages = messages
        )
    }

    @Bean
    fun telegramBotConfig(
        @Value("\${telegram.bot.token}") token: String
    ): TelegramBotConfig {
        return TelegramBotConfig(
            botToken = token
        )
    }
}
