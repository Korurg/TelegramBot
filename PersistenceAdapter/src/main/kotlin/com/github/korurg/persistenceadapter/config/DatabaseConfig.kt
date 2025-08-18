package com.github.korurg.persistenceadapter.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.oshai.kotlinlogging.KotlinLogging
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.babyfish.jimmer.Draft
import org.babyfish.jimmer.sql.DraftInterceptor
import org.babyfish.jimmer.sql.dialect.PostgresDialect
import org.babyfish.jimmer.sql.dialect.SQLiteDialect
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.newKSqlClient
import org.babyfish.jimmer.sql.runtime.ConnectionManager
import org.slf4j.bridge.SLF4JBridgeHandler
import java.util.logging.LogManager
import javax.sql.DataSource

class DatabaseConfig {
    private val logger = KotlinLogging.logger {}

    fun hikariConfig(
        jdbcUrl: String,
        password: String,
        user: String
    ): HikariConfig {
        return HikariConfig().apply {
            this.jdbcUrl = jdbcUrl
            this.password = password
            this.username = user
            this.driverClassName = "org.postgresql.Driver"
            if (isSqlite(jdbcUrl)) {
                this.maximumPoolSize = 1
                this.driverClassName = "org.sqlite.JDBC"
            }
        }
    }

    fun sqlClient(
        dataSource: DataSource,
        interceptors: Collection<DraftInterceptor<out Any, out Draft>>,
    ): KSqlClient {
        return newKSqlClient {
            setDialect(
                when (getDialect(dataSource)) {
                    "sqlite" -> SQLiteDialect()
                    "postgres" -> PostgresDialect()
                    else -> PostgresDialect()
                }
            )
            setConnectionManager(ConnectionManager.simpleConnectionManager(dataSource))
            addDraftInterceptors(interceptors)
        }

    }

    fun hikariDataSource(hikariConfig: HikariConfig): DataSource {
        return HikariDataSource(hikariConfig)
    }

    fun liquibase(dataSource: DataSource): Liquibase {
        dataSource.connection.use {
            val database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(JdbcConnection(it))

            val dialect = getDialect(dataSource)

            val file = if ("sqlite" == dialect) {
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

    private fun getDialect(dataSource: DataSource): String {
        val jdbcUrl = (dataSource as HikariDataSource).jdbcUrl

        var dialect = "postgres"
        if (isSqlite(jdbcUrl)) {
            dialect = "sqlite"
        }
        return dialect
    }

    private fun isSqlite(jdbcUrl: String): Boolean {
        return jdbcUrl.startsWith("jdbc:sqlite:")
    }
}
