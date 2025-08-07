package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.out.GameCommandPort
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId
import com.github.korurg.persistenceadapter.domain.entity.GameEntity
import com.github.korurg.persistenceadapter.domain.entity.steamApp
import com.github.korurg.persistenceadapter.domain.entity.steamId
import com.github.korurg.persistenceadapter.mapper.toGame
import com.github.korurg.persistenceadapter.mapper.toSteamAppEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import java.time.OffsetDateTime


class GameAdapter(
    private val sqlClient: KSqlClient,
    private val defaultDbCoroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GameCommandPort, GameQueryPort {

    override suspend fun saveGame(game: Game) = withContext(defaultDbCoroutineDispatcher) {
        val now = OffsetDateTime.now()
        sqlClient.transaction {
            val gameEntity = GameEntity {
                name = game.name
                maxPlayers = game.maxPlayers
                minPlayers = game.minPlayers

                steamApp = game.steamApp?.toSteamAppEntity()

                updated = now
                created = now
            }


            sqlClient.save(gameEntity, mode = SaveMode.INSERT_IF_ABSENT)
        }
        return@withContext
    }

    override suspend fun findGameBySteamId(steamId: SteamAppExternalId): Game? =
        withContext(defaultDbCoroutineDispatcher) {
            sqlClient.transaction {
                sqlClient.createQuery(GameEntity::class) {
                    where(table.steamApp.steamId eq steamId.value)
                    select(table)
                }.execute().firstOrNull()?.toGame()
            }
        }
}
