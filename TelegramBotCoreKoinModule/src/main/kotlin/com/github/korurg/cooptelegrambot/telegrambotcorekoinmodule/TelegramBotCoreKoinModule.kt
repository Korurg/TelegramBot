package com.github.korurg.cooptelegrambot.telegrambotcorekoinmodule

import com.github.korurg.telegrambot.application.TelegramBotService
import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import org.koin.dsl.module

val telegramBotCoreModule = module {
    single(createdAtStart = true) { TelegramBotService(getAll(), get(), get()) as TelegramMessageReceiveUseCase }
}
