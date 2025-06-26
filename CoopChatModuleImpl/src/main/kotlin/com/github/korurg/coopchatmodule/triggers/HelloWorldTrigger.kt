package com.github.korurg.coopchatmodule.triggers

import com.github.korurg.telegrambot.api.trigger.message.MessageTrigger
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.Update

class HelloWorldTrigger : MessageTrigger() {
    override fun action(data: String, bot: Bot, update: Update, message: Message) {


        bot.sendMessage(ChatId.fromId(message.chat.id), data)

    }
}
