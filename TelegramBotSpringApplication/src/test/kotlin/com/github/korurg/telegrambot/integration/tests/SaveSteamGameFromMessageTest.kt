package com.github.korurg.telegrambot.integration.tests

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.coopchatmodule.domain.id.SteamAppId
import com.github.korurg.ktsteam.dto.AppCategory
import com.github.korurg.ktsteam.dto.AppDetail
import com.github.korurg.ktsteam.dto.AppDetailResult
import com.github.korurg.ktsteam.enums.Language
import com.github.korurg.persistence.entity.tables.references.STEAM_APP
import com.github.korurg.persistence.entity.tables.references.STEAM_APP_CATEGORY
import com.github.korurg.steamadapter.mapper.toAppId
import com.github.korurg.steamadapter.mapper.toSteamAppId
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.integration.BaseIntegrationTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

abstract class SaveSteamGameFromMessageTest : BaseIntegrationTest() {

    @Autowired
    lateinit var telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase

    @Autowired
    lateinit var gameQueryPort: GameQueryPort

    @Test
    fun saveSteamApp_steamAppSaved() {
        runBlocking {
            val steamAppId1 = SteamAppId(1245620)
            val steamAppId2 = SteamAppId(2622380)

            val message1 =
                defaultMessage("Привет, увидел тут игру https://store.steampowered.com/app/${steamAppId1.value}/ELDEN_RING/")
            val message2 =
                defaultMessage("Привет, ещё одна игра https://store.steampowered.com/app/${steamAppId2.value}/ELDEN_RING_NIGHTREIGN/")
            val message3 =
                defaultMessage("Не помню говорил или нет про эту игру https://store.steampowered.com/app/${steamAppId1.value}/ELDEN_RING/")

            val appDetail1 = defaultAppDetail(steamAppId1)
            val appDetail2 = defaultAppDetail(steamAppId2)
                .copy(
                    categories = listOf(
                        defaultAppCategory(),
                        AppCategory(
                            id = 2,
                            description = "solo"
                        )
                    )
                )

            `when`(steamAppDetails.getAppDetail(steamAppId1.toAppId())).thenReturn(
                AppDetailResult.Success(
                    language = Language.RU, appDetail = appDetail1
                )
            )
            `when`(steamAppDetails.getAppDetail(steamAppId2.toAppId())).thenReturn(
                AppDetailResult.Success(
                    language = Language.RU, appDetail = appDetail2
                )
            )

            telegramMessageReceiveUseCase.receiveMessage(message1)
            telegramMessageReceiveUseCase.receiveMessage(message2)
            telegramMessageReceiveUseCase.receiveMessage(message3)

            val actualGame1 = gameQueryPort.findGameBySteamId(steamAppId1)
            val actualGame2 = gameQueryPort.findGameBySteamId(steamAppId2)

            assertThatNotNull(actualGame1)
            assertThatNotNull(actualGame2)
            assertThatTableRecordsCount(STEAM_APP, 2)
            assertThatTableRecordsCount(STEAM_APP_CATEGORY, 2)
            assertThatGameEqualsAppDetail(actualGame1, appDetail1)
            assertThatGameEqualsAppDetail(actualGame2, appDetail2)
        }
    }


    protected fun assertThatGameEqualsAppDetail(actual: Game, expected: AppDetail) {
        assertThat(actual.name).isEqualTo(expected.name)
        val actualSteamApp = actual.steamApp
        assertThatNotNull(actualSteamApp)
        assertThat(actualSteamApp.name).isEqualTo(expected.name)
        assertThat(actualSteamApp.description).isEqualTo(expected.detailedDescription)
        assertThat(actualSteamApp.shortDescription).isEqualTo(expected.shortDescription)
        assertThat(actualSteamApp.fillState).isEqualTo(SteamAppFillState.FILLED)
        assertThat(actualSteamApp.version).isEqualTo(null)
        assertThat(actualSteamApp.steamId).isEqualTo(expected.appId.toSteamAppId())

        val actualPrice = actualSteamApp.currentPrice
        val expectedPrice = expected.priceOverview
        assertThatNotNull(actualPrice)
        assertThatNotNull(expectedPrice)
        assertThat(actualPrice.finalFormatted).isEqualTo(expectedPrice.finalFormatted)
        assertThat(actualPrice.final).isEqualTo(expectedPrice.final)
        assertThat(actualPrice.initial).isEqualTo(expectedPrice.initial)
        assertThat(actualPrice.initialFormatted).isEqualTo(expectedPrice.initialFormatted)
        assertThat(actualPrice.discountPercent).isEqualTo(expectedPrice.discountPercent)

        assertThat(actualSteamApp.categories).hasSize(expected.categories.size)
    }

}
