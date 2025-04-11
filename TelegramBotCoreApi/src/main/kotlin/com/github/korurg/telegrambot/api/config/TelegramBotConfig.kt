package com.github.korurg.telegrambot.api.config

class TelegramBotConfig(
    val extensionPath: String = "./extensions",
    val botToken: String,
    val saveDirectory : String = ".",
    val params: Map<String, String> = emptyMap()
) {

}
