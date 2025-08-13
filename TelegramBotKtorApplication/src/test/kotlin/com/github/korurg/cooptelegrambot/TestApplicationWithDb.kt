package com.github.korurg.cooptelegrambot

import com.github.korurg.cooptelegrambot.ktorapplication.module
import com.github.korurg.ktsteam.KtSteamClient
import com.github.korurg.ktsteam.SteamAppDetails
import com.github.korurg.telegramadapter.adapter.`in`.TelegramBotInAdapter
import com.github.korurg.telegramadapter.adapter.out.TelegramBotOutAdapter
import io.ktor.client.request.*
import io.ktor.server.testing.*
import org.mockito.Mockito
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource

data class TestEnv(
    val app: ApplicationTestBuilder,
    val telegramBotInAdapter: TelegramBotInAdapter,
    val telegramBotOutAdapter: TelegramBotOutAdapter,
    val steamAppDetails: SteamAppDetails
)


fun testApplicationWithDb(block: suspend TestEnv.() -> Unit) {
    //TODO: reuse test container
    val postgres = PostgreSQLContainer("postgres:17")
        .withDatabaseName("coop_telegram_bot")
        .withUsername("postgres")
        .withPassword("postgres")

    postgres.start()

    val telegramBotInAdapter = Mockito.mock(TelegramBotInAdapter::class.java)
    val telegramBotOutAdapter = Mockito.mock(TelegramBotOutAdapter::class.java)

    val ktSteamClient = Mockito.mock(KtSteamClient::class.java)
    val steamAppDetails = Mockito.mock(SteamAppDetails::class.java)

    Mockito.`when`(ktSteamClient.steamAppDetails())
        .thenReturn(steamAppDetails)

    val mockedModule = org.koin.dsl.module() {
        single() { telegramBotInAdapter }
        single() { telegramBotOutAdapter }
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

        block(TestEnv(
            this,
            telegramBotInAdapter,
            telegramBotOutAdapter,
            steamAppDetails
        ))
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
