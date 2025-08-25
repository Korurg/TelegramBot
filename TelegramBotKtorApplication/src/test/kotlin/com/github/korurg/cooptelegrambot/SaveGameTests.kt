package com.github.korurg.cooptelegrambot

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId
import com.github.korurg.ktsteam.dto.*
import com.github.korurg.ktsteam.enums.Language
import com.github.korurg.persistenceadapter.domain.entity.SteamAppCategoryEntity
import com.github.korurg.persistenceadapter.domain.entity.SteamAppEntity
import com.github.korurg.steamadapter.mapper.toAppId
import io.mockk.coEvery
import org.koin.ktor.ext.get
import kotlin.test.Test

class SaveGameTests {

    @Test
    fun `save games by steam link`() = testApplicationWithDb {
        val gameQueryPort = app.application.get<GameQueryPort>()

        val steamAppId1 = SteamAppExternalId(1245620)
        val steamAppId2 = SteamAppExternalId(2622380)

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

        coEvery {
            steamAppDetails.getAppDetail(steamAppId1.toAppId())
        } returns AppDetailResult.Success(
            language = Language.RU, appDetail = appDetail1
        )

        coEvery {
            steamAppDetails.getAppDetail(steamAppId2.toAppId())
        } returns AppDetailResult.Success(
            language = Language.RU, appDetail = appDetail2
        )

        telegramMessageReceiveUseCase.receiveMessage(message1)
        telegramMessageReceiveUseCase.receiveMessage(message2)
        telegramMessageReceiveUseCase.receiveMessage(message3)

        val actualGame1 = gameQueryPort.findGameBySteamId(steamAppId1)
        val actualGame2 = gameQueryPort.findGameBySteamId(steamAppId2)

        assertThatNotNull(actualGame1)
        assertThatNotNull(actualGame2)
        assertThatTableRecordsCount(2, SteamAppEntity::class)
        assertThatTableRecordsCount(2, SteamAppCategoryEntity::class)
        assertThatGameEqualsAppDetail(actualGame1, appDetail1)
        assertThatGameEqualsAppDetail(actualGame2, appDetail2)

        //TODO: assert telegram message via mockk
    }
}
