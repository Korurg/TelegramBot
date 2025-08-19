package com.github.korurg.cooptelegrambot.telegrambotcorekoinmodule

import com.github.korurg.telegrambot.application.TelegramBotService
import com.github.korurg.telegrambot.application.TelegramMessageService
import com.github.korurg.telegrambot.application.TelegramPollService
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.config.L18n14kConfig
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val telegramBotCoreModule = module {
    single(createdAtStart = true) {
        TelegramBotService(
            getAll(),
            get(),
            get()
        ) as TelegramMessageReceiveUseCase
    }

    singleOf(::L18n14kConfig){
        createdAtStart()
    }

    single(createdAtStart = true) {
        get<L18n14kConfig>().defaultConfig()
    }

    singleOf(::TelegramMessageService){
        createdAtStart()
    }

    singleOf(::TelegramPollService){
        createdAtStart()
    }
}
