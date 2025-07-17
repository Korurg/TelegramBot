package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.id.SteamAppId
import com.github.korurg.telegrambot.domain.TelegramMessage

data class SaveSteamAppCommand(
    val message: TelegramMessage?,
    val steamId: SteamAppId,
)
