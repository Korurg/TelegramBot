package com.github.korurg.telegrambot.persistence.config

import javax.sql.DataSource

interface BaseDatabaseConfig {
    fun getDataSource(): DataSource
}
