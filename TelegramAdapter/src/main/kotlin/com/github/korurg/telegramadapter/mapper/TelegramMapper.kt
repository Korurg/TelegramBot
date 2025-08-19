package com.github.korurg.telegramadapter.mapper

import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramPollAnswer
import com.github.korurg.telegrambot.domain.TelegramUser
import com.github.korurg.telegrambot.domain.id.ChatTelegramId
import com.github.korurg.telegrambot.domain.id.MessageId
import com.github.korurg.telegrambot.domain.id.PollOptionTelegramId
import com.github.korurg.telegrambot.domain.id.PollTelegramId
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
    telegramId = ChatTelegramId(id),
    title = title,
    type = type
)


fun PollAnswer.toTelegramPollAnswer() = TelegramPollAnswer(
    pollId = PollTelegramId(pollId),
    answersIds = optionIds.map { PollOptionTelegramId(it) },
    user = user.toTelegramUser(),
)
