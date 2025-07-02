package com.github.korurg.telegrambot.application

import com.github.korurg.telegrambot.application.port.`in`.TelegramMessageReceiveUseCase
import com.github.korurg.telegrambot.domain.TelegramBotReceiveMessageTrigger
import com.github.korurg.telegrambot.domain.TelegramMessage

class TelegramBotService(
    val messageTriggers: List<TelegramBotReceiveMessageTrigger> = ArrayList()
) : TelegramMessageReceiveUseCase {


    override fun receiveMessage(message: TelegramMessage) {
        //TODO: save chat to DB
        //TODO: save user to DB

        messageTriggers.forEach {
            it.handleMessage(message)
        }
    }
}
