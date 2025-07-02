package com.github.korurg.telegrambot.factory

import com.github.korurg.telegrambot.adapter.`in`.TelegramBotInAdapter
import com.github.korurg.telegrambot.adapter.out.TelegramBotOutAdapter
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.config.TelegramBotConfig
import com.github.korurg.telegrambot.domain.TelegramBot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.message

class TelegramBotFactory {
    companion object {

        fun telegramBotOutAdapter(
            telegramBot: TelegramBot
        ): TelegramBotOutAdapter {
            return TelegramBotOutAdapter(telegramBot)
        }

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

            bot.startPolling()
            return TelegramBot(
                bot = bot,
                callback = callback,
                messages = messages
            )
        }
    }
}
