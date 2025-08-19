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
import org.koin.dsl.bind
import org.koin.dsl.module

val coopChatModule = module {
    single(named("commandTrigger"), createdAtStart = true) {
        CommandTrigger(
            get(),
            getAll(),
            get(),
        )
    }.apply {
        bind(TelegramBotReceiveMessageTrigger::class)
    }

    single<TelegramBotReceiveMessageTrigger>(
        named("steamLinkTrigger"),
        createdAtStart = true
    ) { SteamLinkTrigger(get()) }

    singleOf(::GameAddCommandHandler){
        named("gameAddCommandHandler")
        createdAtStart()
    }.apply {
        bind(CommandHandler::class)
    }

    singleOf(::PollStartCommandHandler){
        named("pollStartCommandHandler")
        createdAtStart()
    }.apply {
        bind(CommandHandler::class)
    }


    singleOf(::CommandParserService) {
        createdAtStart()
    }
    singleOf(::SurveyService) {
        createdAtStart()
    }
    singleOf(::GameService) {
        createdAtStart()
    }
}
