package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.persistenceadapter.domain.entity.TelegramChatEntity
import com.github.korurg.persistenceadapter.domain.entity.id
import com.github.korurg.persistenceadapter.domain.entity.telegramId
import com.github.korurg.persistenceadapter.mapper.toTelegramChatEntity
import com.github.korurg.telegrambot.application.port.out.TelegramChatSavePort
import com.github.korurg.telegrambot.domain.TelegramChat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq

class TelegramChatAdapter(
    private val sqlClient: KSqlClient,
    private val defaultDbCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TelegramChatSavePort {
    override suspend fun saveTelegramChat(chat: TelegramChat) = withContext(defaultDbCoroutineDispatcher) {

        sqlClient.transaction {
            val existedChat = sqlClient.createQuery(TelegramChatEntity::class) {
                where(table.telegramId eq chat.telegramId.value)
                select(table.id)
            }.execute().firstOrNull()

            if(existedChat != null) {
                return@transaction
            }

            sqlClient.save(
                chat.toTelegramChatEntity(), mode = SaveMode.INSERT_ONLY
            )
        }

        return@withContext
    }
}
