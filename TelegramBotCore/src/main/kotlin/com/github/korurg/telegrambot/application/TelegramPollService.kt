package com.github.korurg.telegrambot.application

import com.github.korurg.telegrambot.application.port.out.TelegramCommandPort
import com.github.korurg.telegrambot.application.port.out.TelegramPollSavePort
import com.github.korurg.telegrambot.domain.TelegramPoll
import com.github.korurg.telegrambot.domain.TelegramPollOption
import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.id.PollId
import com.github.korurg.telegrambot.domain.id.PollOptionId
import com.github.korurg.telegrambot.domain.id.PollOptionTelegramId

class TelegramPollService(
    private val telegramCommandPort: TelegramCommandPort,
    private val telegramPollSavePort: TelegramPollSavePort,
) {

    suspend fun startPoll(startPollCommand: TelegramStartPollCommand): TelegramPoll {
        val pollTelegramId = telegramCommandPort.startPoll(startPollCommand)

        var optionId = 0

        val telegramPoll = TelegramPoll(
            id = PollId.random(),
            telegramId = pollTelegramId,
            chatTelegramId = startPollCommand.chatTelegramId,
            question = startPollCommand.question,
            options = startPollCommand.options.map {
                TelegramPollOption(
                    id = PollOptionId.random(),
                    telegramId = PollOptionTelegramId(optionId++),
                    option = it,
                )
            },
            isAnonymous = startPollCommand.isAnonymous,
            type = startPollCommand.type,
        )

        val savedPoll = telegramPollSavePort.savePoll(telegramPoll)

        return savedPoll
    }
}
