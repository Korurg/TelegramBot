package com.github.korurg.telegrambot

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TelegramBotApplication : CommandLineRunner {
    @Throws(Exception::class)
    override fun run(vararg args: String?) {
    }
}

fun main(args: Array<String>) {
    runApplication<TelegramBotApplication>(*args)
}
