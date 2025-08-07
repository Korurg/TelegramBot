package com.github.korurg.cooptelegrambot.telegramadapterkoinmodule

import com.github.korurg.telegramadapter.HtmlToTelegramMessageFormatter
import com.github.korurg.telegramadapter.adapter.out.TelegramBotOutAdapter
import com.github.korurg.telegramadapter.config.TelegramAdapterConfig
import com.github.korurg.telegrambot.application.port.out.TelegramMessageSendPort
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DEFAULT_TELEGRAM_COROUTINE_SCOPE = "defaultTelegramCoroutineScope"

val telegramAdapterModule = module {
    single(createdAtStart = true) { TelegramAdapterConfig() }
    single(createdAtStart = true) { get<TelegramAdapterConfig>().telegramBotConfig(getProperty("TELEGRAM_BOT_TOKEN")) }
    single(createdAtStart = true) { get<TelegramAdapterConfig>().telegramBotInAdapter(get(), get()) }
    single(createdAtStart = true) { get<TelegramAdapterConfig>().telegramBot(get()) }
    single(named(DEFAULT_TELEGRAM_COROUTINE_SCOPE), createdAtStart = true) { Dispatchers.IO }
    single(createdAtStart = true) {
        TelegramBotOutAdapter(
            get(),
            get(),
            get(named(DEFAULT_TELEGRAM_COROUTINE_SCOPE))
        ) as TelegramMessageSendPort
    }
    single(createdAtStart = true) { HtmlToTelegramMessageFormatter() }
}
