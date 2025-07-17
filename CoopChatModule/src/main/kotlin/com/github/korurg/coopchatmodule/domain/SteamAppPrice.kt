package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.id.SteamAppInternalId
import com.github.korurg.coopchatmodule.domain.id.SteamAppPriceId
import java.time.OffsetDateTime

data class SteamAppPrice(
    val id: SteamAppPriceId? = null,
    val steamAppId: SteamAppInternalId? = null,
    val currency: String,
    val initial: Long,
    val final: Long,
    val discountPercent: Long,
    val initialFormatted: String,
    val finalFormatted: String,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
