package com.github.korurg.cooptelegrambot.ktorapplication

import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module(properties: Map<String, Any> = mapOf()) {
    configureSerialization()
    configureFrameworks(properties)
    configureRouting()
}
