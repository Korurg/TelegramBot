package com.github.korurg.persistenceadapter.config

import io.github.oshai.kotlinlogging.KotlinLogging
import liquibase.Liquibase
import org.babyfish.jimmer.sql.kt.KSqlClient
import kotlin.system.exitProcess

class DbValidator(
    private val liquibase: Liquibase,
    private val kSqlClient: KSqlClient,
) {

    val logger = KotlinLogging.logger {}

    fun validate() {
        val validationException = kSqlClient.validateDatabase()
        if (validationException != null) {
            for (item in validationException.items) {
                logger.error { item }
            }
        }

    }
}
