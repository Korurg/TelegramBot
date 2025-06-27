package com.github.korurg.telegrambotadapter.adapter

import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambotadapter.adapter.`in`.TelegramBotInAdapter
import com.github.korurg.telegrambotadapter.adapter.out.TelegramBotOutAdapter
import com.github.korurg.telegrambotadapter.domain.TelegramBotConfig
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
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

            val messages: MutableList<suspend com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment.() -> kotlin.Unit> =
                ArrayList()

            val callback: Dispatcher.() -> Unit = {
                message {
                    messages.forEach {
                        it.invoke(this)
                    }
                    println(message.text)
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
