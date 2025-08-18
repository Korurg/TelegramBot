package com.github.korurg.telegramadapter.config

import com.github.korurg.coopchatmodule.config.Commands
import com.github.korurg.telegramadapter.adapter.`in`.TelegramBotInAdapter
import com.github.korurg.telegramadapter.domain.TelegramBot
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.Handler
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.handlers.PollAnswerHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.dispatcher.pollAnswer
import com.github.kotlintelegrambot.entities.BotCommand
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.extensions.filters.Filter


class TelegramAdapterConfig {

    fun telegramBotInAdapter(
        telegramBot: TelegramBot,
        telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase
    ): TelegramBotInAdapter {
        val telegramBotInAdapter = TelegramBotInAdapter(telegramMessageReceiveUseCase)

        telegramBot.messages.add {
            telegramBotInAdapter.receiveMessage().invoke(this)
        }

        telegramBot.pollAnswers.add {
            telegramBotInAdapter.pollAnswer().invoke(this)
        }

        return telegramBotInAdapter
    }


    fun telegramBot(
        telegramBotConfig: TelegramBotConfig,
    ): TelegramBot {

        val messages: MutableList<suspend MessageHandlerEnvironment.() -> Unit> =
            ArrayList()

        val pollAnswers: MutableList<suspend PollAnswerHandlerEnvironment.() -> Unit> =
            ArrayList()

        val callback: Dispatcher.() -> Unit = {
            pollAnswer {
                pollAnswers.forEach {
                    it.invoke(this)
                }
            }

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
            messages = messages,
            pollAnswers = pollAnswers
        )
    }

    fun telegramBotConfig(
        token: String
    ): TelegramBotConfig {
        return TelegramBotConfig(
            botToken = token
        )
    }
}
