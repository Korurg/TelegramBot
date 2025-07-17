package com.github.korurg.telegrambot.persistence.tests

import com.github.korurg.persistence.entity.tables.records.TelegramUserRecord
import com.github.korurg.telegrambot.persistence.BasePersistenceTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import java.time.OffsetDateTime
import kotlin.test.Test

abstract class UserRepositoryTest : BasePersistenceTest() {

    @Test
    @DisplayName("When save telegram user then user saved")
    fun saveTelegramUser_userSaved() {
        val userForSave = createDefaultTelegramUserRecord()

        val savedUser = telegramUserRepository.upsert(userForSave)

        requireNotNull(savedUser.id)

        val findedUser = telegramUserRepository.findById(savedUser.id!!)

        assertThat(findedUser).isNotNull
        requireNotNull(findedUser)

        assertThat(findedUser.id).isEqualTo(savedUser.id)
        assertThat(findedUser.username).isEqualTo(savedUser.username)
        assertThat(findedUser.lastName).isEqualTo(savedUser.lastName)
        assertThat(findedUser.firstName).isEqualTo(savedUser.firstName)
        assertThat(findedUser.telegramId).isEqualTo(savedUser.telegramId)
        assertThat(findedUser.languageCode).isEqualTo(savedUser.languageCode)
        assertThat(findedUser.isBot).isEqualTo(savedUser.isBot)
    }




    private fun createDefaultTelegramUserRecord(): TelegramUserRecord {
        return TelegramUserRecord(
            username = "username",
            lastName = "lastname",
            firstName = "firstname",
            languageCode = "en",
            telegramId = 112233,
            isBot = false,
            updated = OffsetDateTime.now(),
            created = OffsetDateTime.now()
        )
    }
}
