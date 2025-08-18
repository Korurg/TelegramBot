package com.github.korurg.cooptelegrambot.coopchatmodulekoinmodule

import com.github.korurg.coopchatmodule.application.CommandParserService
import com.github.korurg.coopchatmodule.application.GameService
import com.github.korurg.coopchatmodule.application.SurveyService
import com.github.korurg.coopchatmodule.application.commands.CommandHandler
import com.github.korurg.coopchatmodule.application.commands.GameAddCommandHandler
import com.github.korurg.coopchatmodule.application.commands.PollStartCommandHandler
import com.github.korurg.coopchatmodule.application.triggers.CommandTrigger
import com.github.korurg.coopchatmodule.application.triggers.SteamLinkTrigger
import com.github.korurg.telegrambot.application.TelegramBotReceiveMessageTrigger
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coopChatModule = module {
    single<TelegramBotReceiveMessageTrigger>(named("commandTrigger"), createdAtStart = true) {
        CommandTrigger(
            get(),
            get(),
            getAll()
        )
    }
    single<TelegramBotReceiveMessageTrigger>(
        named("steamLinkTrigger"),
        createdAtStart = true
    ) { SteamLinkTrigger(get()) }
    single<CommandHandler>(named("gameAddCommandHandler"), createdAtStart = true) { GameAddCommandHandler(get()) }
    single<CommandHandler>(named("pollStartCommandHandler"), createdAtStart = true) { PollStartCommandHandler(get()) }
    singleOf(::CommandParserService){
        createdAtStart()
    }
    singleOf(::SurveyService){
        createdAtStart()
    }
    singleOf(::GameService){
        createdAtStart()
    }
}
