package com.github.korurg.persistenceadapter.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.babyfish.jimmer.sql.dialect.PostgresDialect
import org.babyfish.jimmer.sql.dialect.SQLiteDialect
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.newKSqlClient
import org.babyfish.jimmer.sql.runtime.ConnectionManager
import org.babyfish.jimmer.sql.transaction.TxConnectionManager
import javax.sql.DataSource

class DatabaseConfig {

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

    fun sqlClient(dataSource: DataSource): KSqlClient {
        return newKSqlClient {
            setDialect(
                when (getDialect(dataSource)) {
                    "sqlite" -> SQLiteDialect()
                    "postgres" -> PostgresDialect()
                    else -> PostgresDialect()
                }
            )
            setConnectionManager(ConnectionManager.simpleConnectionManager(dataSource))

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
