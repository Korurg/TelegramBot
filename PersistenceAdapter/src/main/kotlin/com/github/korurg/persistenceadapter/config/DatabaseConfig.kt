package com.github.korurg.persistenceadapter.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DatabaseConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun hikariConfig(
        @Value($$"${spring.datasource.url}") jdbcUrl: String,
        @Value($$"${spring.datasource.password}") password: String,
        @Value($$"${spring.datasource.username}") user: String
    ): HikariConfig {
        return HikariConfig().apply {
            this.jdbcUrl = jdbcUrl
            this.password = password
            this.username = user
            if (isSqlite(jdbcUrl)) {
                this.maximumPoolSize = 1
            }
        }
    }

    @Bean
    fun hikariDataSource(hikariConfig: HikariConfig): DataSource {
        return HikariDataSource(hikariConfig)
    }

    @Bean
    fun dslContext(dataSource: DataSource): DSLContext {
        return DSL.using(dataSource, getDialect(dataSource))
    }

    private fun getDialect(dataSource: DataSource): SQLDialect {
        val jdbcUrl = (dataSource as HikariDataSource).jdbcUrl

        var dialect = SQLDialect.POSTGRES
        if (isSqlite(jdbcUrl)) {
            dialect = SQLDialect.SQLITE
        }
        return dialect
    }

    @Bean
    fun liquibase(dataSource: DataSource): Liquibase {
        dataSource.connection.use {
            val database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(JdbcConnection(it))

            val dialect = getDialect(dataSource)

            val file = if (SQLDialect.SQLITE == dialect) {
                "sqlite"
            } else {
                "postgresql"
            }

            val liquibase = Liquibase(
                "db/changelog/$file.db.changelog-master.yaml",
                ClassLoaderResourceAccessor(),
                database
            )

            liquibase.update()

            return liquibase
        }
    }

    private fun isSqlite(jdbcUrl: String): Boolean {
        return jdbcUrl.startsWith("jdbc:sqlite:")
    }
}
