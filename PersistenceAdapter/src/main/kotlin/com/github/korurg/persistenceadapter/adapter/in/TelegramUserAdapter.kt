package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.persistenceadapter.domain.entity.TelegramUserEntity
import com.github.korurg.persistenceadapter.domain.entity.id
import com.github.korurg.persistenceadapter.domain.entity.telegramId
import com.github.korurg.persistenceadapter.mapper.toTelegramUserEntity
import com.github.korurg.telegrambot.application.port.out.TelegramUserSavePort
import com.github.korurg.telegrambot.domain.TelegramUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq

class TelegramUserAdapter(
    private val sqlClient: KSqlClient,
    private val defaultDbCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TelegramUserSavePort {
    override suspend fun saveTelegramUser(user: TelegramUser) = withContext(defaultDbCoroutineDispatcher) {

        sqlClient.transaction {
            val existedUser = sqlClient.createQuery(TelegramUserEntity::class) {
                where(table.telegramId eq user.telegramId.value)
                select(table.id)
            }.execute().firstOrNull()

            if (existedUser!=null){
                return@transaction
            }

            sqlClient.save(
                user.toTelegramUserEntity(), mode = SaveMode.INSERT_ONLY
            )
        }

        return@withContext
    }
}
