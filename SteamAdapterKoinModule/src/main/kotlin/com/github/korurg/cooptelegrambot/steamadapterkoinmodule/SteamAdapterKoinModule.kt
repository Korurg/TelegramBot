package com.github.korurg.cooptelegrambot.steamadapterkoinmodule

import com.github.korurg.coopchatmodule.application.port.`in`.SteamAppProviderPort
import com.github.korurg.steamadapter.adapter.out.SteamAppAdapter
import com.github.korurg.steamadapter.config.SteamAdapterConfig
import org.koin.dsl.module

val steamAdapterModule = module {
    single(createdAtStart = true) { SteamAdapterConfig() }
    single(createdAtStart = true) { get<SteamAdapterConfig>().ktSteamClient(get()) }
    single(createdAtStart = true) { get<SteamAdapterConfig>().ktSteamClientConfig(getProperty("STEAM_WEB_API_KEY")) }
    single(createdAtStart = true) { SteamAppAdapter(get()) as SteamAppProviderPort }
}
