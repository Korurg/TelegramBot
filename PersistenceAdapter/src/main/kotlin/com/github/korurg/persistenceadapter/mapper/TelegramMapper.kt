package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.persistenceadapter.domain.entity.TelegramChatEntity
import com.github.korurg.persistenceadapter.domain.entity.TelegramUserEntity
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramUser
import com.github.korurg.telegrambot.domain.id.UserId
import com.github.korurg.telegrambot.domain.id.UserTelegramId


fun TelegramUser.toEntity(): TelegramUserEntity = TelegramUserEntity{
    this@toEntity.id?.let { internalId ->
        id = internalId.value
    }
    telegramId = this@toEntity.telegramId.value
    firstName = this@toEntity.firstName
    lastName = this@toEntity.lastName
    username = this@toEntity.username
    isBot = this@toEntity.isBot
    languageCode = this@toEntity.languageCode
    updated = this@toEntity.updated
    created = this@toEntity.created
}


fun TelegramChat.toTelegramChatEntity(): TelegramChatEntity = TelegramChatEntity {
    this@toTelegramChatEntity.id?.let { internalId ->
        id = internalId.value
    }
    telegramId = this@toTelegramChatEntity.telegramId.value
    title = this@toTelegramChatEntity.title
    type = this@toTelegramChatEntity.type
    updated = this@toTelegramChatEntity.updated
    created = this@toTelegramChatEntity.created
}

fun TelegramChatEntity.toTelegramChat() = TelegramChat(
    id = this@toTelegramChat.id.toChatInternalId(),
    telegramId = this@toTelegramChat.telegramId.toChatExternalId(),
    title = this@toTelegramChat.title,
    type = this@toTelegramChat.type,
    created = this@toTelegramChat.created,
    updated = this@toTelegramChat.updated
)


fun TelegramUserEntity.toDomain() = TelegramUser(
    id = UserId(this@toDomain.id),
    telegramId = UserTelegramId(this@toDomain.telegramId),
    isBot = this@toDomain.isBot,
    firstName = this@toDomain.firstName,
    lastName = this@toDomain.lastName,
    username = this@toDomain.username,
    languageCode = this@toDomain.languageCode,
    updated = this@toDomain.updated,
    created = this@toDomain.created
)
