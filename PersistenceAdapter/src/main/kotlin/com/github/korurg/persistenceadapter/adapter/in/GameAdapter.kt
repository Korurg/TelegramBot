package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.out.GameCommandPort
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId
import com.github.korurg.persistenceadapter.domain.entity.GameEntity
import com.github.korurg.persistenceadapter.domain.entity.by
import com.github.korurg.persistenceadapter.domain.entity.steamApp
import com.github.korurg.persistenceadapter.domain.entity.steamId
import com.github.korurg.persistenceadapter.mapper.toGame
import com.github.korurg.persistenceadapter.mapper.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher


class GameAdapter(
    private val sqlClient: KSqlClient,
    private val defaultDbCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GameCommandPort, GameQueryPort {

    override suspend fun saveGame(game: Game) = withContext(defaultDbCoroutineDispatcher) {
        sqlClient.transaction {
            sqlClient.save(game.toEntity())
        }
        return@withContext
    }

    override suspend fun findGameBySteamId(steamId: SteamAppExternalId): Game? =
        withContext(defaultDbCoroutineDispatcher) {

            val fullGraphFetcher = newFetcher(GameEntity::class).by {
                allScalarFields()
                steamApp {
                    allScalarFields()
                    categories {
                        allScalarFields()
                    }
                    currentPrice {
                        allScalarFields()
                    }
                }
            }

            sqlClient.transaction {
                sqlClient.createQuery(GameEntity::class) {
                    where(table.steamApp.steamId eq steamId.value)
                    select(table.fetch(fullGraphFetcher))
                }.execute().firstOrNull()?.toGame()
            }
        }

    override suspend fun getTopActiveGames(count: Int): List<Game> = withContext(defaultDbCoroutineDispatcher) {
        val fullGraphFetcher = newFetcher(GameEntity::class).by {
            allScalarFields()
            steamApp {
                allScalarFields()
                categories {
                    allScalarFields()
                }
                currentPrice {
                    allScalarFields()
                }
            }
        }

        sqlClient.transaction {
            sqlClient.createQuery(GameEntity::class) {
                select(table.fetch(fullGraphFetcher))
            }.limit(count).execute().map { it.toGame() }
        }
    }
}
