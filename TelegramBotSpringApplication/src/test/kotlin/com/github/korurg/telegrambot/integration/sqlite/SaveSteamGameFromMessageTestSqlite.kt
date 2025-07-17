package com.github.korurg.telegrambot.integration.sqlite

import com.github.korurg.telegrambot.integration.tests.SaveSteamGameFromMessageTest
import org.junit.jupiter.api.DisplayName
import org.springframework.test.context.ActiveProfiles

@DisplayName("[sqlite]")
@ActiveProfiles("sqlite")
class SaveSteamGameFromMessageTestSqlite: SaveSteamGameFromMessageTest() {
}
