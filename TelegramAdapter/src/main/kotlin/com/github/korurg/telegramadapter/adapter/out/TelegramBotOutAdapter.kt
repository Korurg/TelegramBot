package com.github.korurg.telegramadapter.adapter.out

import com.github.korurg.telegramadapter.HtmlToTelegramMessageFormatter
import com.github.korurg.telegramadapter.domain.TelegramBot
import com.github.korurg.telegrambot.application.port.out.TelegramCommandPort
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand
import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.id.PollExternalId
import com.github.kotlintelegrambot.entities.ChatId
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
    ): PollExternalId = withContext(defaultTelegramCoroutineDispatcher) {

        val pollResult = bot.sendPoll(
            chatId = ChatId.fromId(startPollCommand.chatId.value),
            question = startPollCommand.question,
            options = startPollCommand.answers,
            isAnonymous = startPollCommand.isAnonymous
        )

        if (pollResult.isError) {
            throw RuntimeException("Poll creation error")
        }

        pollResult.get().poll?.let {
            return@withContext PollExternalId(it.id.toString())
        }

        throw RuntimeException("Poll creation error")
    }
}
