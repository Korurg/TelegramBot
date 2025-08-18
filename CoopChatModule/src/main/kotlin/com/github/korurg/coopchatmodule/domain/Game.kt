package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.GameState
import com.github.korurg.coopchatmodule.domain.id.GameId
import java.time.OffsetDateTime

data class Game(
    val id: GameId? = null,
    val name: String,
    val maxPlayers: Int,
    val minPlayers: Int,
    val watchPriceChanges: Boolean, //TODO: impl
    val watchPurchases: Boolean, //TODO: impl
    val watchForUpdates: Boolean, //TODO: impl
    val gameState: GameState,
    val steamApp: SteamApp? = null,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
