package com.github.korurg.steamadapter.mapper

import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId
import com.github.korurg.ktsteam.dto.AppId

fun AppId.toSteamAppId(): SteamAppExternalId {
    return SteamAppExternalId(this@toSteamAppId.value)
}

fun SteamAppExternalId.toAppId(): AppId {
    return AppId(this@toAppId.value)
}
