package com.github.korurg.telegrambot.persistence

import com.github.korurg.persistenceadapter.repository.TelegramUserRepository
import com.github.korurg.telegrambot.config.BaseDatabaseConfig
import com.github.korurg.telegrambot.config.PostgresDatabaseConfig
import com.github.korurg.telegrambot.config.SqliteDatabaseConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.env.Environment
import org.springframework.test.annotation.DirtiesContext

@DirtiesContext
@SpringBootTest
@Import(PostgresDatabaseConfig::class, SqliteDatabaseConfig::class)
abstract class BasePersistenceTest {

    @Autowired
    lateinit var databaseConfig: BaseDatabaseConfig

    @Autowired
    lateinit var telegramUserRepository: TelegramUserRepository

    @Autowired
    lateinit var environment: Environment
}
