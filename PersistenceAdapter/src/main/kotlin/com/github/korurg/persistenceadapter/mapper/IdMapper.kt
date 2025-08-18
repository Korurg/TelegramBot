package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.coopchatmodule.domain.id.*
import com.github.korurg.telegrambot.domain.id.ChatExternalId
import com.github.korurg.telegrambot.domain.id.ChatInternalId


fun Long.toGameId() = GameId(this)
fun Long?.toGameId() = this?.let { GameId(it) }
fun Long.toSteamAppInternalId() = SteamAppInternalId(this)
fun Long.toSteamAppExternalId() = SteamAppExternalId(this)
fun Long.toSteamAppPriceId() = SteamAppPriceId(this)
fun Long.toSteamCategoryInternalId() = SteamCategoryInternalId(this)
fun Long.toSteamCategoryExternalId() = SteamCategoryExternalId(this)
fun Long.toChatInternalId() = ChatInternalId(this)
fun Long.toChatExternalId() = ChatExternalId(this)
