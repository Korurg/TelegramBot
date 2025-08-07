package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.persistenceadapter.domain.entity.TelegramChatEntity
import com.github.korurg.persistenceadapter.domain.entity.TelegramUserEntity
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramUser


fun TelegramUser.toTelegramUserEntity(): TelegramUserEntity = TelegramUserEntity{
    this@toTelegramUserEntity.id?.let { internalId ->
        id = internalId.value
    }
    telegramId = this@toTelegramUserEntity.telegramId.value
    firstName = this@toTelegramUserEntity.firstName
    lastName = this@toTelegramUserEntity.lastName
    username = this@toTelegramUserEntity.username
    isBot = this@toTelegramUserEntity.isBot
    languageCode = this@toTelegramUserEntity.languageCode
    updated = this@toTelegramUserEntity.updated
    created = this@toTelegramUserEntity.created
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
