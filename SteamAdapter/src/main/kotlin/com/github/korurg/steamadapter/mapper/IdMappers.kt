package com.github.korurg.steamadapter.mapper

import com.github.korurg.coopchatmodule.domain.id.SteamAppId
import com.github.korurg.ktsteam.dto.AppId

fun AppId.toSteamAppId(): SteamAppId {
    return SteamAppId(this@toSteamAppId.value)
}

fun SteamAppId.toAppId(): AppId {
    return AppId(this@toAppId.value)
}
