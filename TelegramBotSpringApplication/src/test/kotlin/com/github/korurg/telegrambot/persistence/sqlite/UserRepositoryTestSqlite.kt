package com.github.korurg.telegrambot.persistence.sqlite

import com.github.korurg.telegrambot.persistence.tests.UserRepositoryTest
import org.junit.jupiter.api.DisplayName
import org.springframework.test.context.ActiveProfiles

@DisplayName("[sqlite]")
@ActiveProfiles("sqlite")
class UserRepositoryTestSqlite : UserRepositoryTest() {
}
