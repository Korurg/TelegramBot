package com.github.korurg.coopchatmodule.application.port.`in`

import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.id.SteamAppId

fun interface SteamAppProviderPort {
    fun findSteamAppBySteamId(steamId: SteamAppId): SteamApp?
}
