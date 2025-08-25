package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.persistenceadapter.mapper.toDomain
import com.github.korurg.persistenceadapter.mapper.toEntity
import com.github.korurg.telegrambot.application.port.out.TelegramPollSavePort
import com.github.korurg.telegrambot.domain.TelegramPoll
import com.github.korurg.telegrambot.domain.TelegramPollAnswer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.babyfish.jimmer.sql.kt.KSqlClient

class TelegramPollAdapter(
    private val kSqlClient: KSqlClient,
    private val defaultDbCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TelegramPollSavePort {
    override suspend fun savePoll(telegramPoll: TelegramPoll): TelegramPoll =
        withContext(defaultDbCoroutineDispatcher) {
            kSqlClient.transaction {
                val saveResult = kSqlClient.save(telegramPoll.toEntity())
                saveResult.modifiedEntity.toDomain()
            }
        }

    override suspend fun savePollAnswer(pollAnswer: TelegramPollAnswer): TelegramPollAnswer =
        withContext(defaultDbCoroutineDispatcher) {
            kSqlClient.transaction {
                val saveResult = kSqlClient.save(pollAnswer.toEntity())
                saveResult.modifiedEntity.toDomain()
            }
        }
}
