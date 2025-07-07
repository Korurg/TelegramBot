package com.github.korurg.telegrambot.persistence.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.sql.DataSource

@TestConfiguration
@Profile("postgres")
@Testcontainers
class PostgresDatabaseConfig : BaseDatabaseConfig {

    companion object {
        @Container
        @JvmStatic
        val postgres = PostgreSQLContainer("postgres:17")
            .withDatabaseName("coop_telegram_bot")
            .withUsername("postgres")
            .withPassword("postgres")
            .also {
                it.start()
            }
    }

    @Bean
    @Primary
    fun dataSource(): DataSource {
        while (!postgres.isRunning){
            Thread.sleep(1000)
        }
        return HikariDataSource(HikariConfig().apply {
            jdbcUrl = postgres.jdbcUrl
            username = postgres.username
            password = postgres.password
        })
    }

    override fun getDataSource() = dataSource()
}
