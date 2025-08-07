package com.github.korurg.cooptelegrambot.persistenceadapterkoinmodule.config

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.out.GameCommandPort
import com.github.korurg.persistenceadapter.adapter.`in`.GameAdapter
import com.github.korurg.persistenceadapter.adapter.`in`.TelegramChatAdapter
import com.github.korurg.persistenceadapter.adapter.`in`.TelegramUserAdapter
import com.github.korurg.persistenceadapter.config.DatabaseConfig
import com.github.korurg.telegrambot.application.port.out.TelegramChatSavePort
import com.github.korurg.telegrambot.application.port.out.TelegramUserSavePort
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DEFAULT_DB_COROUTINE_SCOPE = "defaultDbCoroutineScope"

val persistenceAdapterModule = module {
    single(createdAtStart = true) { DatabaseConfig() }
    single(named(DEFAULT_DB_COROUTINE_SCOPE), createdAtStart = true) { Dispatchers.IO }
    single(createdAtStart = true) { get<DatabaseConfig>().hikariDataSource(get()) }
    single(createdAtStart = true) {
        get<DatabaseConfig>().hikariConfig(
            jdbcUrl = getProperty("JDBC_URL"),
            password = getProperty("JDBC_PASSWORD"),
            user = getProperty("JDBC_USER"),
        )
    }
    single(createdAtStart = true) { get<DatabaseConfig>().hikariDataSource(get()) }
    single(createdAtStart = true) { get<DatabaseConfig>().liquibase(get()) }
    single(createdAtStart = true) { get<DatabaseConfig>().sqlClient(get()) }


    single(createdAtStart = true) { GameAdapter(get(), get(named(DEFAULT_DB_COROUTINE_SCOPE))) }
    single { get<GameAdapter>() as GameCommandPort }
    single { get<GameAdapter>() as GameQueryPort }


    single(createdAtStart = true) {
        TelegramChatAdapter(
            get(),
            get(named(DEFAULT_DB_COROUTINE_SCOPE))
        ) as TelegramChatSavePort
    }
    single(createdAtStart = true) {
        TelegramUserAdapter(
            get(),
            get(named(DEFAULT_DB_COROUTINE_SCOPE))
        ) as TelegramUserSavePort
    }

}
