package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.coopchatmodule.domain.id.*


fun Long.toGameId(): GameId = GameId(this)
fun Long?.toGameId(): GameId? = this?.let { GameId(it) }
fun Long.toSteamAppInternalId(): SteamAppInternalId = SteamAppInternalId(this)
fun Long.toSteamAppExternalId(): SteamAppExternalId = SteamAppExternalId(this)
fun Long.toSteamAppPriceId(): SteamAppPriceId = SteamAppPriceId(this)
fun Long.toSteamCategoryInternalId(): SteamCategoryInternalId = SteamCategoryInternalId(this)
fun Long.toSteamCategoryExternalId(): SteamCategoryExternalId = SteamCategoryExternalId(this)
