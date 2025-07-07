package com.github.korurg.telegrambot.persistence.postgres

import com.github.korurg.telegrambot.persistence.tests.UserRepositoryTest
import org.junit.jupiter.api.DisplayName
import org.springframework.test.context.ActiveProfiles

@DisplayName("[postgres]")
@ActiveProfiles("postgres")
class UserRepositoryTestPostgres : UserRepositoryTest() {
}
