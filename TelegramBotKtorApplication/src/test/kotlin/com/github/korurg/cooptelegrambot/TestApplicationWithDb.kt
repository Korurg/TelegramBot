package com.github.korurg.cooptelegrambot

import com.github.korurg.cooptelegrambot.ktorapplication.module
import io.ktor.server.testing.*
import org.testcontainers.containers.PostgreSQLContainer

fun testApplicationWithDb(block: suspend ApplicationTestBuilder.() -> Unit) {
    val postgres = PostgreSQLContainer("postgres:17")
        .withDatabaseName("coop_telegram_bot")
        .withUsername("postgres")
        .withPassword("postgres")

    postgres.start()

    return testApplication {
        application {
            module(
                mapOf(
                    Pair("TELEGRAM_BOT_TOKEN", "TELEGRAM_BOT_TOKEN"),
                    Pair("JDBC_URL", postgres.jdbcUrl),
                    Pair("JDBC_PASSWORD", postgres.password),
                    Pair("JDBC_USER", postgres.username),
                    Pair("STEAM_WEB_API_KEY", "STEAM_WEB_API_KEY"),
                )
            )
        }
        block()
    }
}
