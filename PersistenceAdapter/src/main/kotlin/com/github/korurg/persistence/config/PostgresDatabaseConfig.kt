package com.github.korurg.persistence.config

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
class PostgresDatabaseConfig {
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
        }
    }

    @Bean
    fun hikariDataSource(hikariConfig: HikariConfig): DataSource {
        return HikariDataSource(hikariConfig)
    }

    @Bean
    fun dslContext(dataSource: DataSource): DSLContext {
        return DSL.using(dataSource, SQLDialect.POSTGRES)
    }

    @Bean
    fun liquibase(dataSource: DataSource): Liquibase {
        dataSource.connection.use {
            val database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(JdbcConnection(it))

            val liquibase = Liquibase(
                "db/changelog/db.changelog-master.yaml",
                ClassLoaderResourceAccessor(),
                database
            )

            liquibase.update()

            return liquibase
        }
    }
}
