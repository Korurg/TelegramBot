package com.github.korurg.telegrambot.config

import javax.sql.DataSource

interface BaseDatabaseConfig {
    fun getDataSource(): DataSource
}
