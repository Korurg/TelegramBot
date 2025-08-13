package com.github.korurg.persistenceadapter.adapter.out

import com.github.korurg.telegrambot.application.port.`in`.TelegramChatQueryPort
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.id.ChatExternalId

class TelegramChatQueryAdapter: TelegramChatQueryPort {
    override fun findChatByExternalId(telegramId: ChatExternalId): TelegramChat? {
        TODO("Not yet implemented")
    }
}
