package com.github.korurg.cooptelegrambot.coopchatmodulekoinmodule

import com.github.korurg.coopchatmodule.application.CommandParserService
import com.github.korurg.coopchatmodule.application.GameService
import com.github.korurg.coopchatmodule.application.triggers.CommandTrigger
import com.github.korurg.coopchatmodule.application.triggers.SteamLinkTrigger
import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coopChatModule = module {
    single<TelegramBotReceiveMessageTrigger>(named("commandTrigger"), createdAtStart = true) {
        CommandTrigger(
            get(),
            get(),
            get()
        )
    }
    single<TelegramBotReceiveMessageTrigger>(
        named("steamLinkTrigger"),
        createdAtStart = true
    ) { SteamLinkTrigger(get()) }
    single(createdAtStart = true) { CommandParserService() }
    single(createdAtStart = true) { GameService(get(), get(), get(), get()) }
}
