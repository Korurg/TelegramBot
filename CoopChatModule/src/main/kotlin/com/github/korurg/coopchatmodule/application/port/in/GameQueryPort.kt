package com.github.korurg.coopchatmodule.application.port.`in`

import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.id.SteamAppId

interface GameQueryPort {

    suspend fun findGameBySteamId(steamId: SteamAppId): Game?

}
