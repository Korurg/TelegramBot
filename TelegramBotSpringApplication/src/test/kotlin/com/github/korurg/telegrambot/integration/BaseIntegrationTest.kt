package com.github.korurg.telegrambot.integration

import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.SteamAppCategory
import com.github.korurg.coopchatmodule.domain.SteamAppPrice
import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.coopchatmodule.domain.id.SteamAppId
import com.github.korurg.coopchatmodule.domain.id.SteamCategoryId
import com.github.korurg.ktsteam.KtSteamClient
import com.github.korurg.ktsteam.SteamAppDetails
import com.github.korurg.ktsteam.dto.AppCategory
import com.github.korurg.ktsteam.dto.AppDetail
import com.github.korurg.ktsteam.dto.Genre
import com.github.korurg.ktsteam.dto.PriceOverview
import com.github.korurg.ktsteam.enums.ControllerSupport
import com.github.korurg.ktsteam.enums.Currency
import com.github.korurg.ktsteam.enums.PackageType
import com.github.korurg.steamadapter.mapper.toAppId
import com.github.korurg.telegramadapter.adapter.`in`.TelegramBotInAdapter
import com.github.korurg.telegramadapter.adapter.out.TelegramBotOutAdapter
import com.github.korurg.telegrambot.config.BaseDatabaseConfig
import com.github.korurg.telegrambot.config.PostgresDatabaseConfig
import com.github.korurg.telegrambot.config.SqliteDatabaseConfig
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramUser
import com.github.korurg.telegrambot.domain.id.ChatId
import com.github.korurg.telegrambot.domain.id.MessageId
import com.github.korurg.telegrambot.domain.id.UserId
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.jooq.TableRecord
import org.jooq.impl.TableImpl
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.env.Environment
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.bean.override.mockito.MockitoBean
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.test.BeforeTest

@DirtiesContext
@SpringBootTest
@Import(PostgresDatabaseConfig::class, SqliteDatabaseConfig::class)
abstract class BaseIntegrationTest {

    @Autowired
    lateinit var databaseConfig: BaseDatabaseConfig

    @Autowired
    lateinit var environment: Environment

    @Autowired
    lateinit var dslContext: DSLContext

    @MockitoBean
    lateinit var ktSteamClient: KtSteamClient

    @MockitoBean
    lateinit var telegramBotInAdapter: TelegramBotInAdapter

    @MockitoBean
    lateinit var telegramBotOutAdapter: TelegramBotOutAdapter

    lateinit var steamAppDetails: SteamAppDetails

    @BeforeTest
    fun prepare() {
        steamAppDetails = Mockito.mock(SteamAppDetails::class.java)
        `when`(ktSteamClient.steamAppDetails())
            .thenReturn(steamAppDetails)
    }


    @OptIn(ExperimentalContracts::class)
    protected final fun <T> assertThatNotNull(t: T) {
        contract {
            returns() implies (t != null)
        }
        assertThat(t).isNotNull
    }

    protected fun <TABLE : TableImpl<RECORD>, RECORD : TableRecord<RECORD>> assertThatTableRecordsCount(
        table: TABLE,
        expectedCount: Int
    ) {
        val actualCount = dslContext.selectCount()
            .from(table)
            .fetchOne(0, Int::class.java)

        assertThat(actualCount)
            .`as`("Check records count for table ${table.name}")
            .isEqualTo(expectedCount)
    }

    protected fun defaultAppDetail(steamAppId: SteamAppId): AppDetail = AppDetail(
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

    protected fun defaultSteamApp(steamAppId: SteamAppId): SteamApp {
        return SteamApp(
            steamId = steamAppId,
            name = "Elden ring",
            type = "game",
            categories = listOf(
                SteamAppCategory(
                    steamCategoryId = SteamCategoryId(1),
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

    protected fun defaultMessage(text: String): TelegramMessage {
        return TelegramMessage(
            messageId = MessageId(1),
            chat = TelegramChat(
                id = ChatId(1),
                title = "Test chat",
                type = "group"
            ),
            text = text,
            from = TelegramUser(
                id = UserId(1),
                isBot = false,
                firstName = "Test firstname",
                lastName = "Test lastname",
                username = "test",
                languageCode = "en"
            )
        )
    }
}
