package com.github.korurg.cooptelegrambot

import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.SteamAppCategory
import com.github.korurg.coopchatmodule.domain.SteamAppPrice
import com.github.korurg.coopchatmodule.domain.enums.GameState
import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId
import com.github.korurg.coopchatmodule.domain.id.SteamCategoryExternalId
import com.github.korurg.cooptelegrambot.ktorapplication.module
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
import com.github.korurg.steamadapter.mapper.toSteamAppId
import com.github.korurg.telegramadapter.adapter.`in`.TelegramBotInAdapter
import com.github.korurg.telegramadapter.adapter.out.TelegramBotOutAdapter
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.application.port.out.TelegramCommandPort
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import com.github.korurg.telegrambot.domain.TelegramChat
import com.github.korurg.telegrambot.domain.TelegramMessage
import com.github.korurg.telegrambot.domain.TelegramUser
import com.github.korurg.telegrambot.domain.id.ChatTelegramId
import com.github.korurg.telegrambot.domain.id.MessageId
import com.github.korurg.telegrambot.domain.id.UserTelegramId
import io.kotest.assertions.withClue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.ktor.client.request.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.koin.dsl.bind
import org.koin.ktor.ext.get
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.random.Random
import kotlin.reflect.KClass

data class TestEnv(
    val app: ApplicationTestBuilder,
    val telegramBotInAdapter: TelegramBotInAdapter,
    val telegramBotOutAdapter: TelegramBotOutAdapter,
    val steamAppDetails: SteamAppDetails,
    val telegramMessageReceiveUseCase: TelegramMessageReceiveUseCase,
    val kSqlClient: KSqlClient
) {

    fun <T : Any> save(vararg entities: T): List<T> {
        return kSqlClient.transaction {
            entities.map {
                val saveResult = kSqlClient.save(it)
                saveResult.modifiedEntity
            }
        }
    }

    suspend inline fun <reified T : Any> save(
        noinline block: suspend () -> T
    ): T {
        val entity = block()
        val saveResult = kSqlClient.save(entity)
        return saveResult.modifiedEntity
    }

    suspend inline fun <reified T : Any> saveAll(
        noinline block: suspend () -> List<T>
    ): List<T> {
        val entities = block()

        return kSqlClient.transaction {
            entities.map {
                val saveResult = kSqlClient.save(it)
                saveResult.modifiedEntity
            }
        }
    }

    fun assertThatGameEqualsAppDetail(actual: Game, expected: AppDetail) {
        actual.name shouldBeEqual expected.name
        actual.steamApp shouldNotBeNull {
            name shouldBeEqual expected.name
            description shouldNotBeNull {
                this shouldBeEqual expected.detailedDescription
            }
            shortDescription shouldNotBeNull {
                this shouldBeEqual expected.shortDescription
            }

            fillState shouldBeEqual SteamAppFillState.FILLED
            version.shouldBeNull()
            steamId shouldBeEqual expected.appId.toSteamAppId()

            currentPrice shouldNotBeNull {
                val expectedPrice = expected.priceOverview.shouldNotBeNull()

                finalFormatted shouldBeEqual expectedPrice.finalFormatted
                final shouldBeEqual expectedPrice.final
                initial shouldBeEqual expectedPrice.initial
                initialFormatted shouldBeEqual expectedPrice.initialFormatted
                discountPercent shouldBeEqual expectedPrice.discountPercent
            }

            categories shouldHaveSize expected.categories.size
        }
    }

    fun defaultAppDetail(steamAppId: SteamAppExternalId): AppDetail = AppDetail(
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

    fun defaultAppCategory(): AppCategory {
        return AppCategory(
            id = 1,
            description = "coop"
        )
    }

    fun defaultGame(steamAppId: SteamAppExternalId?): Game {
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

    fun defaultSteamApp(steamAppId: SteamAppExternalId): SteamApp {
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

    fun defaultChatExternalId() = ChatTelegramId(1)

    fun defaultMessage(text: String): TelegramMessage {
        return TelegramMessage(
            messageId = MessageId(Random.nextLong()),
            chat = TelegramChat(
                telegramId = defaultChatExternalId(),
                title = "Test chat",
                type = "group"
            ),
            text = text,
            from = defaultTelegramUser()
        )
    }


    fun defaultTelegramUser(userTelegramId: UserTelegramId = UserTelegramId(1)): TelegramUser {
        return TelegramUser(
            telegramId = userTelegramId,
            isBot = false,
            firstName = "Test firstname",
            lastName = "Test lastname",
            username = "test",
            languageCode = "en"
        )
    }

    @OptIn(ExperimentalContracts::class)
    fun <T> assertThatNotNull(t: T) {
        contract {
            returns() implies (t != null)
        }
        assertThat(t).isNotNull
    }

    fun <T : Any> assertThatTableRecordsCount(
        expectedCount: Long,
        entity: KClass<T>,
    ) {
        val actualCount = kSqlClient.createQuery(entity) {
            select(count(table))
        }.fetchOne()

        assertThat(actualCount)
            .`as`("Check records count for table ${entity.simpleName}")
            .isEqualTo(expectedCount)
    }

    inline infix fun <reified T : KClass<out Any>> T.tableShouldBeSize(expectedSize: Long) {
        val actualCount = kSqlClient.createQuery(this) {
            select(count(table))
        }.fetchOne()


        withClue("Table ${this.simpleName} size should be equal $expectedSize but now $actualCount"){
            actualCount shouldBeEqual expectedSize
        }
    }

    fun defaultUsers(count: Long): List<TelegramUser> {
        val range = 1..count

        return range.map {
            defaultTelegramUser(UserTelegramId(it)).copy(
                firstName = "firstName$it",
                lastName = "lastName$it",
                username = "username$it",
            )
        }
    }
}


fun testApplicationWithDb(block: suspend TestEnv.() -> Unit) {
    //TODO: reuse test container
    val postgres = PostgreSQLContainer("postgres:17")
        .withDatabaseName("coop_telegram_bot")
        .withUsername("postgres")
        .withPassword("postgres")

    postgres.start()


    val telegramBotInAdapter = mockk<TelegramBotInAdapter>()
    val telegramBotOutAdapter = mockk<TelegramBotOutAdapter>()

    val ktSteamClient = mockk<KtSteamClient>()
    val steamAppDetails = mockk<SteamAppDetails>()

    every { ktSteamClient.steamAppDetails() } returns steamAppDetails

    val mockedModule = org.koin.dsl.module() {
        single() { telegramBotInAdapter }
        single() { telegramBotOutAdapter }.apply {
            bind(TelegramMessageSendPort::class)
            bind(TelegramCommandPort::class)
        }
        single() { ktSteamClient }
    }

    testApplication {
        application {
            module(
                mapOf(
                    Pair("TELEGRAM_BOT_TOKEN", "FAKE_TELEGRAM_BOT_TOKEN"),
                    Pair("JDBC_URL", postgres.jdbcUrl),
                    Pair("JDBC_PASSWORD", postgres.password),
                    Pair("JDBC_USER", postgres.username),
                    Pair("STEAM_WEB_API_KEY", "FAKE_STEAM_WEB_API_KEY"),
                ),
                listOf(mockedModule)
            )
        }

        //forcefully initialize Koin
        client.get("/fake")

        val telegramMessageReceiveUseCase = application.get<TelegramMessageReceiveUseCase>()
        val kSqlClient = application.get<KSqlClient>()

        block(
            TestEnv(
                this,
                telegramBotInAdapter,
                telegramBotOutAdapter,
                steamAppDetails,
                telegramMessageReceiveUseCase,
                kSqlClient
            )
        )
    }

}

private fun clearDb(dataSource: DataSource) {
    val connection = dataSource.connection
    try {
        val meta = connection.metaData
        val tables = connection.prepareStatement(
            "SELECT table_name FROM information_schema.tables " +
                    "WHERE table_schema = 'public' AND table_type = 'BASE TABLE'"
        ).use { stmt ->
            stmt.executeQuery().let { rs ->
                mutableListOf<String>().apply {
                    while (rs.next()) add(rs.getString(1))
                }
            }
        }

        connection.createStatement().use { stmt ->
            tables.forEach { table ->
                stmt.execute("TRUNCATE TABLE $table CASCADE")
            }
        }
    } finally {
        connection.close()
    }
}
