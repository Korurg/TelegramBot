package com.github.korurg.telegramadapter.mapper

import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramPollAnswer
import com.github.korurg.telegrambot.domain.TelegramUser
import com.github.korurg.telegrambot.domain.id.ChatExternalId
import com.github.korurg.telegrambot.domain.id.MessageId
import com.github.korurg.telegrambot.domain.id.PollOptionExternalId
import com.github.korurg.telegrambot.domain.id.PollExternalId
import com.github.korurg.telegrambot.domain.id.UserExternalId
import com.github.kotlintelegrambot.entities.Chat
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.User
import com.github.kotlintelegrambot.entities.polls.PollAnswer


fun Message.toTelegramMessage() = TelegramMessage(
    messageId = MessageId(messageId),
    chat = chat.toTelegramChat(),
    text = text,
    from = from?.toTelegramUser()
)


fun User.toTelegramUser() = TelegramUser(
    telegramId = UserExternalId(id),
    isBot = isBot,
    firstName = firstName,
    lastName = lastName,
    username = username,
    languageCode = languageCode
)

fun Chat.toTelegramChat() = TelegramChat(
    telegramId = ChatExternalId(id),
    title = title,
    type = type
)


fun PollAnswer.toTelegramPollAnswer() = TelegramPollAnswer(
    pollId = PollExternalId(pollId),
    answersIds = optionIds.map { PollOptionExternalId(it) },
    user = user.toTelegramUser(),
)
