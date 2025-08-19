package com.github.korurg.persistenceadapter.adapter.out

import com.github.korurg.persistenceadapter.domain.entity.TelegramChatEntity
import com.github.korurg.persistenceadapter.domain.entity.telegramId
import com.github.korurg.persistenceadapter.mapper.toTelegramChat
import com.github.korurg.telegrambot.application.port.`in`.TelegramChatQueryPort
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.id.ChatTelegramId
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq

class TelegramChatQueryAdapter(
    private val kSqlClient: KSqlClient
) : TelegramChatQueryPort {
    override fun findChatByExternalId(telegramId: ChatTelegramId): TelegramChat? {
        return kSqlClient.createQuery(TelegramChatEntity::class){
                where(table.telegramId eq telegramId.value)
                select(table)
            }.fetchOne()?.toTelegramChat()
    }
}
