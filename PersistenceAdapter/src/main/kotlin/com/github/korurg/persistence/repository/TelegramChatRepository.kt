package com.github.korurg.persistence.repository

import com.github.korurg.persistence.entity.tables.records.TelegramChatRecord
import com.github.korurg.persistence.entity.tables.references.TELEGRAM_CHAT
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class TelegramChatRepository(
    private val dslContext: DSLContext,
) {
    fun findById(id: Long): TelegramChatRecord? {
        return dslContext.selectFrom(TELEGRAM_CHAT)
            .where(TELEGRAM_CHAT.ID.eq(id))
            .fetchOne()
            ?.into(TelegramChatRecord::class.java)
    }

    fun upsertByTelegramId(telegramChatRecord: TelegramChatRecord) {

        dslContext.transaction { config ->
            val transactionalDsl = DSL.using(config)

            val exists = transactionalDsl.selectOne()
                .from(TELEGRAM_CHAT)
                .where(TELEGRAM_CHAT.TELEGRAM_ID.eq(telegramChatRecord.telegramId))
                .fetchOne() != null

            if (exists) {
                transactionalDsl.update(TELEGRAM_CHAT)
                    .set(TELEGRAM_CHAT.TITLE, telegramChatRecord.title)
                    .set(TELEGRAM_CHAT.TYPE, telegramChatRecord.type)
                    .set(TELEGRAM_CHAT.UPDATED, OffsetDateTime.now())
                    .where(TELEGRAM_CHAT.TELEGRAM_ID.eq(telegramChatRecord.telegramId))
                    .execute()
            } else {
                transactionalDsl.insertInto(TELEGRAM_CHAT)
                    .set(telegramChatRecord)
                    .set(TELEGRAM_CHAT.UPDATED, OffsetDateTime.now())
                    .set(TELEGRAM_CHAT.CREATED, OffsetDateTime.now())
                    .execute()
            }

        }
    }
}
