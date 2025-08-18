package com.github.korurg.cooptelegrambot

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.SteamAppCategory
import com.github.korurg.coopchatmodule.domain.SteamAppPrice
import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId
import com.github.korurg.coopchatmodule.domain.id.SteamCategoryExternalId
import com.github.korurg.ktsteam.dto.*
import com.github.korurg.ktsteam.enums.ControllerSupport
import com.github.korurg.ktsteam.enums.Currency
import com.github.korurg.ktsteam.enums.Language
import com.github.korurg.ktsteam.enums.PackageType
import com.github.korurg.persistenceadapter.domain.entity.SteamAppCategoryEntity
import com.github.korurg.persistenceadapter.domain.entity.SteamAppEntity
import com.github.korurg.steamadapter.mapper.toAppId
import com.github.korurg.steamadapter.mapper.toSteamAppId
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramUser
import com.github.korurg.telegrambot.domain.id.ChatExternalId
import com.github.korurg.telegrambot.domain.id.MessageId
import com.github.korurg.telegrambot.domain.id.UserExternalId
import io.mockk.coEvery
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.koin.ktor.ext.get
import org.mockito.Mockito.`when`
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.reflect.KClass
import kotlin.test.Test

class SaveGameTests : BaseTest() {

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
        assertThatTableRecordsCount(2, SteamAppEntity::class, kSqlClient)
        assertThatTableRecordsCount(2, SteamAppCategoryEntity::class, kSqlClient)
        assertThatGameEqualsAppDetail(actualGame1, appDetail1)
        assertThatGameEqualsAppDetail(actualGame2, appDetail2)
    }
}
