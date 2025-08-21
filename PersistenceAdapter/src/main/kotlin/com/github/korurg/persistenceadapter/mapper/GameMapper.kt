package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.SteamAppCategory
import com.github.korurg.coopchatmodule.domain.SteamAppPrice
import com.github.korurg.coopchatmodule.domain.enums.GameState
import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.coopchatmodule.domain.id.SteamCategoryExternalId
import com.github.korurg.coopchatmodule.domain.id.SteamCategoryInternalId
import com.github.korurg.persistenceadapter.domain.entity.GameEntity
import com.github.korurg.persistenceadapter.domain.entity.SteamAppCategoryEntity
import com.github.korurg.persistenceadapter.domain.entity.SteamAppEntity
import com.github.korurg.persistenceadapter.domain.entity.SteamAppPriceEntity

fun GameEntity.toGame(): Game = Game(
    id = this.id.toGameId(),
    name = this.name,
    maxPlayers = this.maxPlayers,
    minPlayers = this.minPlayers,
    watchPriceChanges = true,
    watchPurchases = true,
    watchForUpdates = true,
    gameState = GameState.NEW,
    steamApp = this.steamApp?.toSteamApp(),
    updated = this.updated,
    created = this.created,
)

fun Game.toEntity()= GameEntity {
    this@toEntity.id?.let {
        id = it.value
    }
    name = this@toEntity.name
    maxPlayers = this@toEntity.maxPlayers
    minPlayers = this@toEntity.minPlayers
    steamApp = this@toEntity.steamApp?.toSteamAppEntity()
}

fun SteamAppEntity.toSteamApp(): SteamApp = SteamApp(
    id = this.id.toSteamAppInternalId(),
    steamId = this.steamId.toSteamAppExternalId(),
    name = this.name,
    type = this.type,
    categories = this.categories.map { categoryEntity -> categoryEntity.toSteamAppCategory() },
    currentPrice = this.currentPrice?.toSteamAppPrice(),
    fillState = SteamAppFillState.valueOf(this.fillState),
    version = this.version,
    description = this.description,
    shortDescription = this.shortDescription,
    updated = this.updated,
    created = this.created,
)

fun SteamAppPriceEntity.toSteamAppPrice(): SteamAppPrice = SteamAppPrice(
    id = this.id.toSteamAppPriceId(),
    currency = this.currency,
    initial = this.initial,
    final = this.final,
    discountPercent = this.discountPercent,
    initialFormatted = this.initialFormatted,
    finalFormatted = this.finalFormatted,
    updated = this.updated,
    created = this.created,
)

fun SteamAppCategoryEntity.toSteamAppCategory(): SteamAppCategory = SteamAppCategory(
    id = SteamCategoryInternalId(this.id),
    steamId = SteamCategoryExternalId(this.steamId),
    description = this.description,
    updated = this.updated,
    created = this.created
)

fun SteamApp.toSteamAppEntity(): SteamAppEntity = SteamAppEntity {
    created = this@toSteamAppEntity.created
    this@toSteamAppEntity.id?.let {
        id = it.value
    }
    name = this@toSteamAppEntity.name
    type = this@toSteamAppEntity.type
    steamId = this@toSteamAppEntity.steamId.value
    version = this@toSteamAppEntity.version
    description = this@toSteamAppEntity.description
    shortDescription = this@toSteamAppEntity.shortDescription
    fillState = this@toSteamAppEntity.fillState.name
    currentPrice = this@toSteamAppEntity.currentPrice?.toSteamAppPriceEntity()
    categories = this@toSteamAppEntity.categories.map { it.toSteamAppCategoryEntity() }
    updated = this@toSteamAppEntity.updated
}

fun SteamAppPrice.toSteamAppPriceEntity(): SteamAppPriceEntity = SteamAppPriceEntity {
    currency = this@toSteamAppPriceEntity.currency
    initialFormatted = this@toSteamAppPriceEntity.initialFormatted
    finalFormatted = this@toSteamAppPriceEntity.finalFormatted
    final = this@toSteamAppPriceEntity.final
    initial = this@toSteamAppPriceEntity.initial
    discountPercent = this@toSteamAppPriceEntity.discountPercent
    updated = this@toSteamAppPriceEntity.updated
    created = this@toSteamAppPriceEntity.created
}

fun SteamAppCategory.toSteamAppCategoryEntity(): SteamAppCategoryEntity = SteamAppCategoryEntity {
    steamId = this@toSteamAppCategoryEntity.steamId.value
    description = this@toSteamAppCategoryEntity.description
    updated = this@toSteamAppCategoryEntity.updated
    created = this@toSteamAppCategoryEntity.created
}
