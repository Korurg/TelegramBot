package com.github.korurg.coopchatmodule.application.commands

import com.github.korurg.coopchatmodule.application.SurveyService
import com.github.korurg.coopchatmodule.config.Commands
import com.github.korurg.coopchatmodule.domain.Command
import com.github.korurg.telegrambot.domain.TelegramMessage

class PollStartCommandHandler(
    private val surveyService: SurveyService
) : CommandHandler {
    override suspend fun handle(
        command: Command,
        message: TelegramMessage
    ) {
        surveyService.startDefaultPoll(message.chat.telegramId)
    }

    override fun getCommand(): String {
        return Commands.POLL_START
    }
}
