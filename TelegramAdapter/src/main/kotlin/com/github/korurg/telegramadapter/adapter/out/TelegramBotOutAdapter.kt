package com.github.korurg.telegramadapter.adapter.out

import com.github.korurg.telegramadapter.HtmlToTelegramMessageFormatter
import com.github.korurg.telegramadapter.domain.TelegramBot
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramSendMessageCommand
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class TelegramBotOutAdapter(
    private val telegramBot: TelegramBot,
    private val htmlToTelegramMessageFormatter: HtmlToTelegramMessageFormatter,
    private val defaultTelegramCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TelegramMessageSendPort {

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
            chatId = ChatId.Companion.fromId(sendMessageCommand.chatId.value),
            text = text,
            replyToMessageId = sendMessageCommand.replyToMessageId?.value
        )

        Unit
    }
}
