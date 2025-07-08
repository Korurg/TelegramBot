package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.persistence.entity.tables.records.TelegramChatRecord
import com.github.korurg.persistenceadapter.repository.TelegramChatRepository
import com.github.korurg.telegrambot.application.port.out.TelegramChatSavePort
import com.github.korurg.telegrambot.domain.TelegramChat
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class TelegramChatAdapter(
    private val telegramChatRepository: TelegramChatRepository

) : TelegramChatSavePort {
    override fun saveTelegramChat(chat: TelegramChat) {
        telegramChatRepository.upsertByTelegramId(
            TelegramChatRecord(
                id = null,
                telegramId = chat.id.value,
                title = chat.title,
                type = chat.type,
                updated = OffsetDateTime.now(),
                created = OffsetDateTime.now(),
            )
        )
    }
}
