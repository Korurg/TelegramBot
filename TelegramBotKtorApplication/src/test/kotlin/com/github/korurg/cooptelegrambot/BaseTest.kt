package com.github.korurg.cooptelegrambot

import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.SteamAppCategory
import com.github.korurg.coopchatmodule.domain.SteamAppPrice
import com.github.korurg.coopchatmodule.domain.enums.GameState
import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId
import com.github.korurg.coopchatmodule.domain.id.SteamCategoryExternalId
import com.github.korurg.ktsteam.dto.AppCategory
import com.github.korurg.ktsteam.dto.AppDetail
import com.github.korurg.ktsteam.dto.Genre
import com.github.korurg.ktsteam.dto.PriceOverview
import com.github.korurg.ktsteam.enums.ControllerSupport
import com.github.korurg.ktsteam.enums.Currency
import com.github.korurg.ktsteam.enums.PackageType
import com.github.korurg.steamadapter.mapper.toAppId
import com.github.korurg.steamadapter.mapper.toSteamAppId
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramUser
import com.github.korurg.telegrambot.domain.id.ChatTelegramId
import com.github.korurg.telegrambot.domain.id.MessageId
import com.github.korurg.telegrambot.domain.id.UserExternalId
import org.assertj.core.api.Assertions.assertThat
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.reflect.KClass

open class BaseTest {

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

    protected fun defaultAppDetail(steamAppId: SteamAppExternalId): AppDetail = AppDetail(
        appId = steamAppId.toAppId(),
        type = PackageType.GAME,
        name = "Elden Ring",
        requiredAge = 0,
        isFree = false,
        controllerSupport = ControllerSupport.NOT_PRESENT,
        dlc = listOf(),
        detailedDescription = "test game",
        aboutTheGame = "about game",
        shortDescription = "game",
        supportedLanguages = "en, ru",
        priceOverview = PriceOverview(
            currency = Currency.RUB,
            initial = 100,
            final = 100,
            discountPercent = 0,
            initialFormatted = "",
            finalFormatted = "100 руб."
        ),
        packages = listOf(),
        categories = listOf(
            defaultAppCategory()
        ),
        genres = listOf(
            Genre(
                id = 1,
                description = "action"
            )
        ),
    )

    protected fun defaultAppCategory(): AppCategory {
        return AppCategory(
            id = 1,
            description = "coop"
        )
    }

    protected fun defaultGame(steamAppId: SteamAppExternalId?): Game {
        return Game(
            name = "Elden Ring",
            maxPlayers = 4,
            minPlayers = 4,
            watchPriceChanges = true,
            watchPurchases = true,
            watchForUpdates = true,
            gameState = GameState.ACTIVE,
            steamApp = steamAppId?.let { defaultSteamApp(it) }
        )
    }

    protected fun defaultSteamApp(steamAppId: SteamAppExternalId): SteamApp {
        return SteamApp(
            steamId = steamAppId,
            name = "Elden ring",
            type = "game",
            categories = listOf(
                SteamAppCategory(
                    steamId = SteamCategoryExternalId(1),
                    description = "coop"
                )
            ),
            currentPrice = SteamAppPrice(
                currency = "RUB",
                initial = 100,
                final = 100,
                discountPercent = 0,
                initialFormatted = "",
                finalFormatted = "100 руб."
            ),
            fillState = SteamAppFillState.FILLED,
            version = null,
            description = "super game",
            shortDescription = "game",
        )
    }

    protected fun defaultChatExternalId() = ChatTelegramId(1)

    protected fun defaultMessage(text: String): TelegramMessage {
        return TelegramMessage(
            messageId = MessageId(1),
            chat = TelegramChat(
                telegramId = defaultChatExternalId(),
                title = "Test chat",
                type = "group"
            ),
            text = text,
            from = TelegramUser(
                telegramId = UserExternalId(1),
                isBot = false,
                firstName = "Test firstname",
                lastName = "Test lastname",
                username = "test",
                languageCode = "en"
            )
        )
    }

    @OptIn(ExperimentalContracts::class)
    protected fun <T> assertThatNotNull(t: T) {
        contract {
            returns() implies (t != null)
        }
        assertThat(t).isNotNull
    }

    protected fun <T : Any> assertThatTableRecordsCount(
        expectedCount: Long,
        entity: KClass<T>,
        kSqlClient: KSqlClient,
    ) {
        val actualCount = kSqlClient.createQuery(entity) {
            select(count(table))
        }.fetchOne()

        assertThat(actualCount)
            .`as`("Check records count for table ${entity.simpleName}")
            .isEqualTo(expectedCount)
    }

}
