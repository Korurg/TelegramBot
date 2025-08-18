package com.github.korurg.cooptelegrambot

import com.github.korurg.cooptelegrambot.ktorapplication.module
import com.github.korurg.ktsteam.KtSteamClient
import com.github.korurg.ktsteam.SteamAppDetails
import com.github.korurg.telegramadapter.adapter.`in`.TelegramBotInAdapter
import com.github.korurg.telegramadapter.adapter.out.TelegramBotOutAdapter
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.application.port.out.TelegramCommandPort
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import io.ktor.client.request.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.koin.dsl.bind
import org.koin.ktor.ext.get
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource

data class TestEnv(
    val app: ApplicationTestBuilder,
    val telegramBotInAdapter: TelegramBotInAdapter,
    val telegramBotOutAdapter: TelegramBotOutAdapter,
    val steamAppDetails: SteamAppDetails,
    val telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase,
    val kSqlClient: KSqlClient
)

fun testApplicationWithDb(block: suspend TestEnv.() -> Unit) {
    //TODO: reuse test container
    val postgres = PostgreSQLContainer("postgres:17")
        .withDatabaseName("coop_telegram_bot")
        .withUsername("postgres")
        .withPassword("postgres")

    postgres.start()


    val telegramBotInAdapter = mockk<TelegramBotInAdapter>()
    val telegramBotOutAdapter = mockk<TelegramBotOutAdapter>()

    val ktSteamClient = mockk<KtSteamClient>()
    val steamAppDetails = mockk<SteamAppDetails>()

    every { ktSteamClient.steamAppDetails() } returns steamAppDetails

    val mockedModule = org.koin.dsl.module() {
        single() { telegramBotInAdapter }
        single() { telegramBotOutAdapter }.apply {
            bind(TelegramMessageSendPort::class)
            bind(TelegramCommandPort::class)
        }
        single() { ktSteamClient }
    }

    testApplication {
        application {
            module(
                mapOf(
                    Pair("TELEGRAM_BOT_TOKEN", "FAKE_TELEGRAM_BOT_TOKEN"),
                    Pair("JDBC_URL", postgres.jdbcUrl),
                    Pair("JDBC_PASSWORD", postgres.password),
                    Pair("JDBC_USER", postgres.username),
                    Pair("STEAM_WEB_API_KEY", "FAKE_STEAM_WEB_API_KEY"),
                ),
                listOf(mockedModule)
            )
        }

        //forcefully initialize Koin
        client.get("/fake")

        val telegramMessageReceiveUseCase = application.get<TelegramMessageReceiveUseCase>()
        val kSqlClient = application.get<KSqlClient>()

        block(
            TestEnv(
                this,
                telegramBotInAdapter,
                telegramBotOutAdapter,
                steamAppDetails,
                telegramMessageReceiveUseCase,
                kSqlClient
            )
        )
    }

}

private fun clearDb(dataSource: DataSource) {
    val connection = dataSource.connection
    try {
        val meta = connection.metaData
        val tables = connection.prepareStatement(
            "SELECT table_name FROM information_schema.tables " +
                    "WHERE table_schema = 'public' AND table_type = 'BASE TABLE'"
        ).use { stmt ->
            stmt.executeQuery().let { rs ->
                mutableListOf<String>().apply {
                    while (rs.next()) add(rs.getString(1))
                }
            }
        }

        connection.createStatement().use { stmt ->
            tables.forEach { table ->
                stmt.execute("TRUNCATE TABLE $table CASCADE")
            }
        }
    } finally {
        connection.close()
    }
}
