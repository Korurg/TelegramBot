package com.github.korurg.telegrambot.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

class PostgresDatabaseConfig : DatabaseConfig {

    override fun getHikariConfig(params: Map<String, Object>): HikariConfig {
        val config = HikariConfig()

        config.connectionTimeout = 30000
        config.idleTimeout = 600000
        config.maxLifetime = 1800000
        config.maximumPoolSize = 1

        return config
    }

    override fun getHikariDataSource(hikariConfig: HikariConfig): HikariDataSource {
        return HikariDataSource(hikariConfig)
    }

    override fun getDslContext(dataSource: DataSource): DSLContext {
        return DSL.using(dataSource, SQLDialect.POSTGRES)
    }

    override fun getDslContext(params: Map<String, Object>): DSLContext {
        return getDslContext(getHikariDataSource(getHikariConfig(params)))
    }
}
