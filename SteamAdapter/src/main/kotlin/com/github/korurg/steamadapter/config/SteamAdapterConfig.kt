package com.github.korurg.steamadapter.config

import com.github.korurg.ktsteam.KtSteamClient
import com.github.korurg.ktsteam.KtSteamClientFactory
import com.github.korurg.ktsteam.config.KtSteamClientConfig
import com.github.korurg.ktsteam.enums.Language
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.github.korurg.steamadapter")
class SteamAdapterConfig {

    @Bean
    fun ktSteamClientConfig(
        @Value($$"${steam.webapikey}") steamWebApiKey: String
    ): KtSteamClientConfig {
        return KtSteamClientConfig(
            defaultLanguage = Language.RU,
            steamWebApiKey = steamWebApiKey
        )
    }

    @Bean
    fun ktSteamClient(ktSteamClientConfig: KtSteamClientConfig): KtSteamClient {
        return KtSteamClientFactory.createKtSteamClient(ktSteamClientConfig)
    }
}
