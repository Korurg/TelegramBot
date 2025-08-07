package com.github.korurg.coopchatmodule.application.port.`in`

import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId

fun interface SteamAppProviderPort {
    fun findSteamAppBySteamId(steamId: SteamAppExternalId): SteamApp?
}
