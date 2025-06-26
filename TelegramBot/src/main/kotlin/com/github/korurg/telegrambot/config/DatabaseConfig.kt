package com.github.korurg.telegrambot.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import javax.sql.DataSource

interface DatabaseConfig {
    fun getHikariDataSource(hikariConfig: HikariConfig): HikariDataSource
    fun getDslContext(dataSource: DataSource): DSLContext
    fun getHikariConfig(params: Map<String, Object>): HikariConfig
    fun getDslContext(params: Map<String, Object>): DSLContext
}
