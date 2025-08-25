package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.persistenceadapter.domain.entity.TelegramChatEntity
import com.github.korurg.persistenceadapter.domain.entity.TelegramPollAnswerEntity
import com.github.korurg.persistenceadapter.domain.entity.TelegramPollEntity
import com.github.korurg.persistenceadapter.domain.entity.TelegramPollOptionEntity
import com.github.korurg.telegrambot.domain.TelegramPoll
import com.github.korurg.telegrambot.domain.TelegramPollAnswer
import com.github.korurg.telegrambot.domain.TelegramPollOption
import com.github.korurg.telegrambot.domain.enums.PollType
import com.github.korurg.telegrambot.domain.id.*

fun TelegramPoll.toEntity() = TelegramPollEntity {
    this@toEntity.id?.let {
        id = it.value
    }
    telegramId = this@toEntity.telegramId.value
    chat = TelegramChatEntity { telegramId = this@toEntity.chatTelegramId.value }
    question = this@toEntity.question
    isAnonymous = this@toEntity.isAnonymous
    type = this@toEntity.type.name
    options = this@toEntity.options.map { it.toEntity() }
}

fun TelegramPollOption.toEntity() = TelegramPollOptionEntity {
    id = this@toEntity.id.value
    telegramId = this@toEntity.telegramId.value
    option = this@toEntity.option
}

fun TelegramPollEntity.toDomain() = TelegramPoll(
    id = PollId(id),
    telegramId = PollTelegramId(telegramId),
    chatTelegramId = ChatTelegramId(chat.telegramId),
    question = question,
    options = options.map { it.toDomain() },
    isAnonymous = isAnonymous,
    type = PollType.valueOf(type),
    updated = updated,
    created = created,
)

fun TelegramPollOptionEntity.toDomain() = TelegramPollOption(
    id = PollOptionId(id),
    telegramId = PollOptionTelegramId(telegramId),
    option = option
)

fun TelegramPollAnswer.toEntity() = TelegramPollAnswerEntity{
    user = this@toEntity.user.toEntity()
//    pollOption = this@toEntity.pollOptionTelegramId
}
