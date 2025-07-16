package com.github.korurg.telegrambot.integration.postgres

import com.github.korurg.telegrambot.integration.tests.SaveSteamGameFromMessageTest
import org.junit.jupiter.api.DisplayName
import org.springframework.test.context.ActiveProfiles

@DisplayName("[postgres]")
@ActiveProfiles("postgres")
class SaveSteamGameFromMessageTestPostgres : SaveSteamGameFromMessageTest() {
}
