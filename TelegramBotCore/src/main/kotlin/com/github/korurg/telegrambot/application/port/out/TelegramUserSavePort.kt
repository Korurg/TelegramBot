package com.github.korurg.telegrambot.application.port.out

import com.github.korurg.telegrambot.domain.TelegramUser

interface TelegramUserSavePort {
    fun saveTelegramUser(user: TelegramUser)
}
