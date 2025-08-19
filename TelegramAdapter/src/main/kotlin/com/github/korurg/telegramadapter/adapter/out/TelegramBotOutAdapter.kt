package com.github.korurg.telegramadapter.adapter.out

import com.github.korurg.telegramadapter.HtmlToTelegramMessageFormatter
import com.github.korurg.telegramadapter.domain.TelegramBot
import com.github.korurg.telegrambot.application.port.out.TelegramCommandPort
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand
import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.id.PollTelegramId
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.polls.PollType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TelegramBotOutAdapter(
    private val telegramBot: TelegramBot,
    private val htmlToTelegramMessageFormatter: HtmlToTelegramMessageFormatter,
    private val defaultTelegramCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TelegramMessageSendPort, TelegramCommandPort {

    private val bot = telegramBot.bot

    override suspend fun sendMessage(
        sendMessageCommand: TelegramSendMessageCommand
    ) = withContext(defaultTelegramCoroutineDispatcher) {
        val text = if (sendMessageCommand.htmlToTelegramFormat) {
            htmlToTelegramMessageFormatter.format(sendMessageCommand.text)
        } else {
            sendMessageCommand.text
        }

        bot.sendMessage(
            chatId = ChatId.fromId(sendMessageCommand.chatId.value),
            text = text,
            replyToMessageId = sendMessageCommand.replyToMessageId?.value
        )

        Unit
    }

    override suspend fun startPoll(
        startPollCommand: TelegramStartPollCommand
    ): PollTelegramId = withContext(defaultTelegramCoroutineDispatcher) {
        val pollResult = bot.sendPoll(
            chatId = ChatId.fromId(startPollCommand.chatTelegramId.value),
            question = startPollCommand.question,
            options = startPollCommand.options,
            isAnonymous = startPollCommand.isAnonymous,
            type = when(startPollCommand.type){
                com.github.korurg.telegrambot.domain.enums.PollType.QUIZ ->PollType.QUIZ
                com.github.korurg.telegrambot.domain.enums.PollType.REGULAR -> PollType.REGULAR
            }
        )

        if (pollResult.isError) {
            throw RuntimeException("Poll creation error")
        }

        pollResult.get().poll?.let {
            return@withContext PollTelegramId(it.id.toString())
        }

        throw RuntimeException("Poll creation error")
    }
}
