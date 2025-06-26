package com.github.korurg.telegrambotadapter

import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.dispatcher.pollAnswer
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.entities.polls.PollType

class TelegramBotAdapter(
    private val telegramBotConfig: TelegramBotConfig,
    private val telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase
) {
    private val bot = bot {
        token = telegramBotConfig.botToken
        dispatch {
            message {
                telegramMessageReceiveUseCase.receiveMessage(
                    TelegramMessage(
                        chat = TelegramChat(
                            chatId = com.github.korurg.telegrambot.domain.id.ChatId(message.chat.id)
                        )
                    )
                )
            }

            pollAnswer {
                println("${pollAnswer.user.username} has selected the option ${pollAnswer.optionIds.lastOrNull()} in the poll ${pollAnswer.pollId}")
            }

            message {
                bot.sendPoll(
                    chatId = ChatId.Companion.fromId(message.chat.id),
                    type = PollType.REGULAR,
                    question = "Java or Kotlin?",
                    options = listOf("Java", "Kotlin"),
                    isAnonymous = false
                )
            }

            command("settings") {

                val chatId = update.message?.chat?.id ?: return@command

                val inlineKeyboard = InlineKeyboardMarkup.Companion.create(
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
                    chatId = ChatId.Companion.fromId(chatId),
                    text = "Выберите действие:",
                    replyMarkup = inlineKeyboard
                )
            }

            command("regularPoll") {

                bot.sendPoll(
                    chatId = ChatId.Companion.fromId(message.chat.id),
                    question = "Pizza with or without pineapple?",
                    options = listOf("With :(", "Without :)"),
                    isAnonymous = false,
                )
            }
        }
    }

    init {
        bot.startPolling()
    }
}
