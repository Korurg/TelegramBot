package com.github.korurg.persistenceadapter.repository

import com.github.korurg.persistence.entity.tables.records.TelegramUserRecord
import com.github.korurg.persistence.entity.tables.references.TELEGRAM_USER
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class TelegramUserRepository(
    private val dslContext: DSLContext,
) : AbstractJooqRepository<TelegramUserRecord, Long?>(
    dslContext,
    TELEGRAM_USER,
    TELEGRAM_USER.ID
) {
    fun upsertByTelegramId(telegramUserRecord: TelegramUserRecord) {

        dslContext.transaction { config ->
            val transactionalDsl = DSL.using(config)

            val exists = transactionalDsl.selectOne()
                .from(TELEGRAM_USER)
                .where(TELEGRAM_USER.TELEGRAM_ID.eq(telegramUserRecord.telegramId))
                .fetchOne() != null

            if (exists) {
                transactionalDsl.update(TELEGRAM_USER)
                    .set(TELEGRAM_USER.USERNAME, telegramUserRecord.username)
                    .set(TELEGRAM_USER.LANGUAGE_CODE, telegramUserRecord.languageCode)
                    .set(TELEGRAM_USER.LAST_NAME, telegramUserRecord.lastName)
                    .set(TELEGRAM_USER.FIRST_NAME, telegramUserRecord.firstName)
                    .set(TELEGRAM_USER.UPDATED, OffsetDateTime.now())
                    .where(TELEGRAM_USER.TELEGRAM_ID.eq(telegramUserRecord.telegramId))
                    .execute()
            } else {
                transactionalDsl.insertInto(TELEGRAM_USER)
                    .set(telegramUserRecord)
                    .set(TELEGRAM_USER.UPDATED, OffsetDateTime.now())
                    .set(TELEGRAM_USER.CREATED, OffsetDateTime.now())
                    .execute()
            }

        }
    }
}
