package com.github.korurg.telegrambot.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
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

    @Bean
    @Primary
    fun dslContext(dataSource: DataSource): DSLContext {
        return DSL.using(dataSource, SQLDialect.SQLITE)
    }

    override fun getDataSource() = dataSource()
}
