package com.github.korurg.telegrambotadapter.adapter

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.Dispatcher

class TelegramBot internal constructor(
    internal val bot: Bot,
    internal val callback: Dispatcher.() -> Unit,
    internal val messages: MutableList<suspend com.github.kotlintelegrambot.dispatcher.handlers.MessageHandlerEnvironment.() -> kotlin.Unit>,
)
