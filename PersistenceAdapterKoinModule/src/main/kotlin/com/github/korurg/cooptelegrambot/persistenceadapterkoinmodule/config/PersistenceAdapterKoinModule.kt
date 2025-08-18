package com.github.korurg.cooptelegrambot.persistenceadapterkoinmodule.config

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.out.GameCommandPort
import com.github.korurg.coopchatmodule.application.port.out.SurveyCommandPort
import com.github.korurg.persistenceadapter.adapter.`in`.GameAdapter
import com.github.korurg.persistenceadapter.adapter.`in`.SurveyAdapter
import com.github.korurg.persistenceadapter.adapter.`in`.TelegramChatAdapter
import com.github.korurg.persistenceadapter.adapter.`in`.TelegramUserAdapter
import com.github.korurg.persistenceadapter.adapter.out.TelegramChatQueryAdapter
import com.github.korurg.persistenceadapter.config.DatabaseConfig
import com.github.korurg.persistenceadapter.config.interceptor.AuditableInterceptor
import com.github.korurg.telegrambot.application.port.`in`.TelegramChatQueryPort
import com.github.korurg.telegrambot.application.port.out.TelegramChatSavePort
import com.github.korurg.telegrambot.application.port.out.TelegramUserSavePort
import kotlinx.coroutines.Dispatchers
import org.babyfish.jimmer.Draft
import org.babyfish.jimmer.sql.DraftInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
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
    single(createdAtStart = true) { get<DatabaseConfig>().sqlClient(get(), getAll()) }

    single<DraftInterceptor<out Any, out Draft>>(
        named("auditableInterceptor"),
        createdAtStart = true
    ) { AuditableInterceptor() }

    single(createdAtStart = true) {
        GameAdapter(
            get(),
            get(named(DEFAULT_DB_COROUTINE_SCOPE))
        )
    }
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

    singleOf(::TelegramChatQueryAdapter).apply {
        bind(TelegramChatQueryPort::class)
    }

    singleOf(::SurveyAdapter).apply {
        bind(SurveyCommandPort::class)
    }

}
