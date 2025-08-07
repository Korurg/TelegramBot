package com.github.korurg.steamadapter.config

import com.github.korurg.ktsteam.KtSteamClient
import com.github.korurg.ktsteam.KtSteamClientFactory
import com.github.korurg.ktsteam.config.KtSteamClientConfig
import com.github.korurg.ktsteam.enums.Language

class SteamAdapterConfig {

    fun ktSteamClientConfig(
        steamWebApiKey: String
    ): KtSteamClientConfig {
        return KtSteamClientConfig(
            defaultLanguage = Language.RU,
            steamWebApiKey = steamWebApiKey
        )
    }

    fun ktSteamClient(ktSteamClientConfig: KtSteamClientConfig): KtSteamClient {
        return KtSteamClientFactory.createKtSteamClient(ktSteamClientConfig)
    }
}
