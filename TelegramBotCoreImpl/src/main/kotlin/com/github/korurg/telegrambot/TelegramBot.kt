package com.github.korurg.telegrambot

import com.github.korurg.telegrambot.api.config.TelegramBotConfig
import com.github.korurg.telegrambot.api.extension.TelegramBotExtension
import com.github.korurg.telegrambot.extension.ExtensionLoader
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.dispatcher.pollAnswer
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton

class TelegramBot(private val telegramBotConfig: TelegramBotConfig) {
    private val extensionLoader: ExtensionLoader = ExtensionLoader(telegramBotConfig.extensionPath)
    private val extensions: List<TelegramBotExtension> = extensionLoader.loadExtensions()

    private val bot = bot {
        token = telegramBotConfig.botToken
        dispatch {
            extensions.forEach {
                message {
                    it.handleMessage(bot, update, message)
                }
            }

            pollAnswer {
                println("${pollAnswer.user.username} has selected the option ${pollAnswer.optionIds.lastOrNull()} in the poll ${pollAnswer.pollId}")
            }

            message {

            }

            command("settings") {

                val chatId = update.message?.chat?.id ?: return@command

                val inlineKeyboard = InlineKeyboardMarkup.create(
                    listOf(
                        InlineKeyboardButton.CallbackData(
                            text = "Опция 1",
                            callbackData = "option_1"
                        ),
                        InlineKeyboardButton.CallbackData(
                            text = "Опция 2",
                            callbackData = "option_2"
                        )
                    ),
                    listOf(
                        InlineKeyboardButton.Url(
                            text = "Открыть сайт",
                            url = "https://example.com"
                        )
                    )
                )

                bot.sendMessage(
                    chatId = ChatId.fromId(chatId),
                    text = "Выберите действие:",
                    replyMarkup = inlineKeyboard
                )
            }

            command("regularPoll") {

                bot.sendPoll(
                    chatId = ChatId.fromId(message.chat.id),
                    question = "Pizza with or without pineapple?",
                    options = listOf("With :(", "Without :)"),
                    isAnonymous = false,
                )
            }
        }
    }

    fun start() {

        bot.startPolling()
    }
}
