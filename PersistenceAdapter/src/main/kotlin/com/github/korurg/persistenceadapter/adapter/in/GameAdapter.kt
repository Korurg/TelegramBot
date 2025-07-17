package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.out.GameCommandPort
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.SteamAppCategory
import com.github.korurg.coopchatmodule.domain.SteamAppPrice
import com.github.korurg.coopchatmodule.domain.id.GameId
import com.github.korurg.coopchatmodule.domain.id.SteamAppId
import com.github.korurg.coopchatmodule.domain.id.SteamAppInternalId
import com.github.korurg.persistence.entity.tables.records.SteamAppSteamAppCategoryRecord
import com.github.korurg.persistence.entity.tables.references.*
import com.github.korurg.persistenceadapter.mapper.GameMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.kotlin.coroutines.transactionCoroutine
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class GameAdapter(
    private val dslContext: DSLContext,
    private val gameMapper: GameMapper,
    private val defaultDbCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GameCommandPort, GameQueryPort {

    @Suppress("kotlin:S6311")
    override suspend fun saveGame(game: Game): Game = withContext(defaultDbCoroutineDispatcher) {
        saveGame(game, dslContext)
    }

    @Suppress("kotlin:S6311")
    override suspend fun saveSteamApp(steamApp: SteamApp): SteamApp = withContext(defaultDbCoroutineDispatcher) {
        saveSteamApp(steamApp, dslContext)
    }

    override suspend fun findGameBySteamId(steamId: SteamAppId): Game? {
        return dslContext.transactionCoroutine { configuration ->
            val ctx = DSL.using(configuration)

            val (gameRecord, steamAppRecord) = ctx.select()
                .from(GAME)
                .join(STEAM_APP).on(GAME.STEAM_APP_ID.eq(STEAM_APP.ID))
                .where(STEAM_APP.STEAM_ID.eq(steamId.value))
                .fetchOne()
                ?.map { record ->
                    Pair(
                        record.into(GAME),
                        record.into(STEAM_APP),
                    )
                } ?: return@transactionCoroutine null


            val steamAppPrice = findSteamAppPrice(SteamAppInternalId(gameRecord.id!!), ctx)
            val steamAppCategories = findSteamAppCategoriesBySteamAppId(SteamAppInternalId(gameRecord.id!!), ctx)

            gameMapper.toGame(
                gameRecord = gameRecord
            ).copy(
                steamApp = gameMapper.toSteamApp(steamAppRecord)
                    .copy(
                        currentPrice = steamAppPrice,
                        categories = steamAppCategories
                    )
            )
        }
    }

    private fun findSteamAppCategoriesBySteamAppId(
        steamAppId: SteamAppInternalId,
        ctx: DSLContext
    ): List<SteamAppCategory> {
        val categories = ctx.select()
            .from(STEAM_APP_CATEGORY)
            .join(STEAM_APP_STEAM_APP_CATEGORY)
            .on(STEAM_APP_STEAM_APP_CATEGORY.STEAM_APP_CATEGORY_ID.eq(STEAM_APP_CATEGORY.ID))
            .where(STEAM_APP_STEAM_APP_CATEGORY.STEAM_APP_ID.eq(steamAppId.value))
            .fetch()
            .map { record ->
                record.into(STEAM_APP_CATEGORY)
            }
            .map {
                gameMapper.toSteamAppCategory(it)
            }

        return categories
    }

    suspend fun findSteamAppPrice(steamAppId: SteamAppInternalId, dslContext: DSLContext): SteamAppPrice? {
        return dslContext.selectFrom(STEAM_APP_PRICE)
            .where(STEAM_APP_PRICE.STEAM_APP_ID.eq(steamAppId.value))
            .fetchOne()
            ?.let {
                gameMapper.toSteamAppPrice(it)
            }
    }

    suspend fun saveGame(game: Game, dslContext: DSLContext): Game {
        return dslContext.transactionCoroutine { configuration ->
            val ctx = DSL.using(configuration)

            val savedSteamApp = game.steamApp?.let {
                saveSteamApp(it, ctx)
            }

            val saved = ctx.insertInto(GAME)
                .set(gameMapper.toGameRecord(game).apply {
                    steamAppId = savedSteamApp?.id?.value
                })
                .returning()
                .fetchOne()
                ?: error("Failed insert to ${GAME.name}")

            game.copy(
                id = GameId(saved.id!!),
                steamApp = savedSteamApp
            )
        }
    }

    suspend fun saveSteamApp(steamApp: SteamApp, dslContext: DSLContext): SteamApp {
        return dslContext.transactionCoroutine { configuration ->
            val ctx = DSL.using(configuration)

            val existedSteamApp = ctx.selectFrom(STEAM_APP)
                .where(STEAM_APP.STEAM_ID.eq(steamApp.steamId.value))
                .fetchOne()


            val steamAppRecord = gameMapper.toSteamAppRecord(steamApp).apply {
                existedSteamApp?.id?.let {
                    id = it
                }
                created = existedSteamApp?.created ?: OffsetDateTime.now()
                updated = OffsetDateTime.now()
            }

            val newSteamApp = ctx.insertInto(STEAM_APP)
                .set(steamAppRecord)
                .onDuplicateKeyUpdate()
                .set(steamAppRecord)
                .returning()
                .fetchOne()
                ?.let { gameMapper.toSteamApp(it) }
                ?: error("Failed insert to steam_app $steamAppRecord")

            val savedSteamAppPrice = steamApp.currentPrice?.let {
                saveSteamAppPrice(it.copy(steamAppId = newSteamApp.id), ctx)
            }

            val savedCategories = steamApp.categories.map { category ->
                val savedCategory = saveSteamAppCategory(category, ctx)
                savedCategory
            }

            linkSteamAppAndSteamAppCategory(newSteamApp, savedCategories, ctx)

            newSteamApp.copy(
                currentPrice = savedSteamAppPrice,
                categories = savedCategories
            )
        }
    }

    fun linkSteamAppAndSteamAppCategory(
        steamApp: SteamApp,
        steamAppCategories: Iterable<SteamAppCategory>,
        dslContext: DSLContext
    ) {
        dslContext.deleteFrom(STEAM_APP_STEAM_APP_CATEGORY)
            .where(STEAM_APP_STEAM_APP_CATEGORY.STEAM_APP_ID.eq(steamApp.id!!.value))

        steamAppCategories.forEach { category ->
            dslContext.insertInto(STEAM_APP_STEAM_APP_CATEGORY)
                .set(
                    SteamAppSteamAppCategoryRecord(
                        steamAppId = steamApp.id!!.value,
                        steamAppCategoryId = category.id!!.value,
                        updated = OffsetDateTime.now(),
                        created = OffsetDateTime.now(),
                    )
                )
                .execute()
        }
    }

    suspend fun saveSteamAppCategory(steamAppCategory: SteamAppCategory, dslContext: DSLContext): SteamAppCategory {
        return dslContext.transactionCoroutine { configuration ->
            val ctx = DSL.using(configuration)
            ctx.selectFrom(STEAM_APP_CATEGORY)
                .where(STEAM_APP_CATEGORY.STEAM_ID.eq(steamAppCategory.steamCategoryId.value))
                .fetchOne()
                ?.let { return@transactionCoroutine gameMapper.toSteamAppCategory(it) }

            ctx.insertInto(STEAM_APP_CATEGORY)
                .set(gameMapper.toSteamAppCategoryRecord(steamAppCategory).apply {
                    updated = OffsetDateTime.now()
                    created = OffsetDateTime.now()
                })
                .returning()
                .fetchOne()
                ?.let { return@transactionCoroutine gameMapper.toSteamAppCategory(it) }
                ?: error("Failed insert to steam_app_category $steamAppCategory")
        }
    }

    fun saveSteamAppPrice(steamAppPrice: SteamAppPrice, dslContext: DSLContext): SteamAppPrice {
        return dslContext.insertInto(STEAM_APP_PRICE)
            .set(gameMapper.toSteamAppPriceRecord(steamAppPrice).apply {
                created = OffsetDateTime.now()
                updated = OffsetDateTime.now()
            })
            .returning()
            .fetchOne()
            ?.let {
                gameMapper.toSteamAppPrice(it)
            } ?: error("Failed insert to ${STEAM_APP_PRICE.name}")
    }
}
