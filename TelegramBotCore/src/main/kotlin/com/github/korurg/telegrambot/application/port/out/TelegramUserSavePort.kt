package com.github.korurg.telegrambot.application.port.out

import com.github.korurg.telegrambot.domain.TelegramUser

interface TelegramUserSavePort {
    suspend fun saveTelegramUser(user: TelegramUser)
}
