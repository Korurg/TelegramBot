package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.id.SteamCategoryExternalId
import com.github.korurg.coopchatmodule.domain.id.SteamCategoryInternalId
import java.time.OffsetDateTime

data class SteamAppCategory(
    val id: SteamCategoryInternalId? = null,
    val steamId: SteamCategoryExternalId,
    val description: String,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
