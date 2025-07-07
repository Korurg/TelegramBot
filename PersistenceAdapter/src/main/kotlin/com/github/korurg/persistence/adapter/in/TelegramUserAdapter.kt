package com.github.korurg.persistence.adapter.`in`

import com.github.korurg.persistence.entity.tables.records.TelegramUserRecord
import com.github.korurg.persistence.repository.TelegramUserRepository
import com.github.korurg.telegrambot.application.port.out.TelegramUserSavePort
import com.github.korurg.telegrambot.domain.TelegramUser
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class TelegramUserAdapter(
    private val telegramUserRepository: TelegramUserRepository
) : TelegramUserSavePort {


    override fun saveTelegramUser(user: TelegramUser) {
        telegramUserRepository.upsertByTelegramId(
            TelegramUserRecord(
                id = null,
                telegramId = user.id.value,
                firstName = user.firstName,
                lastName = user.lastName,
                username = user.username,
                isBot = user.isBot,
                languageCode = user.languageCode,
                updated = OffsetDateTime.now(),
                created = OffsetDateTime.now()
            )
        )
    }
}
