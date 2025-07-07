package com.github.korurg.telegrambot.application.port.out

import com.github.korurg.telegrambot.domain.TelegramChat

interface TelegramChatSavePort {
    fun saveTelegramChat(chat: TelegramChat)
}
