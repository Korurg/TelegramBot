package com.github.korurg.telegrambot.application.port.`in`

import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.id.ChatExternalId

interface TelegramChatQueryPort {
    fun findChatByExternalId(telegramId: ChatExternalId): TelegramChat?
}
