package com.github.korurg.coopchatmodule.application.port.`in`

import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId

interface GameQueryPort {

    suspend fun findGameBySteamId(steamId: SteamAppExternalId): Game?
    suspend fun getTopActiveGames(count: Int): List<Game>
}
