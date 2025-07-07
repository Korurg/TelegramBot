package com.github.korurg.telegrambot.persistence.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@TestConfiguration
@Profile("sqlite")
class SqliteDatabaseConfig : BaseDatabaseConfig {

    @Bean
    @Primary
    fun dataSource(): DataSource {
        return HikariDataSource(HikariConfig().apply {
            jdbcUrl = "jdbc:sqlite::memory:"
            driverClassName = "org.sqlite.JDBC"
            maximumPoolSize = 1
        })
    }

    override fun getDataSource() = dataSource()
}
