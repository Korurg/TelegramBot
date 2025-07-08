package com.github.korurg.telegramadapter.domain

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment

class TelegramBot internal constructor(
    internal val bot: Bot,
    internal val callback: Dispatcher.() -> Unit,
    internal val messages: MutableList<suspend MessageHandlerEnvironment.() -> Unit>,
)
