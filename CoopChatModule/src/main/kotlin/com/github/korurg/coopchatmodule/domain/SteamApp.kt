package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.coopchatmodule.domain.id.SteamAppId
import com.github.korurg.coopchatmodule.domain.id.SteamAppInternalId
import java.time.OffsetDateTime

data class SteamApp(
    val id: SteamAppInternalId? = null,
    val steamId: SteamAppId,
    val name: String,
    val type: String?,
    val categories: List<SteamAppCategory> = listOf(),
    val currentPrice: SteamAppPrice?,
    val fillState: SteamAppFillState,
    val version: Int?,
    val description: String?,
    val shortDescription: String?,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
