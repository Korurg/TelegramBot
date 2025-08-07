package com.github.korurg.cooptelegrambot.ktorapplication

import com.github.korurg.cooptelegrambot.coopchatmodulekoinmodule.coopChatModule
import com.github.korurg.cooptelegrambot.persistenceadapterkoinmodule.config.persistenceAdapterModule
import com.github.korurg.cooptelegrambot.steamadapterkoinmodule.steamAdapterModule
import com.github.korurg.cooptelegrambot.telegramadapterkoinmodule.telegramAdapterModule
import com.github.korurg.cooptelegrambot.telegrambotcorekoinmodule.telegramBotCoreModule
import io.ktor.server.application.*
import org.koin.environmentProperties
import org.koin.fileProperties
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        fileProperties()
        environmentProperties()
        slf4jLogger()
        modules(
            persistenceAdapterModule,
            telegramAdapterModule,
            steamAdapterModule,
            coopChatModule,
            telegramBotCoreModule,
        )
    }
}
