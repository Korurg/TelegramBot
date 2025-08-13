package com.github.korurg.cooptelegrambot.ktorapplication

import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.core.module.Module

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module(
    properties: Map<String, Any> = mapOf(),
    modules: List<Module> = listOf()
) {
    configureSerialization()
    configureFrameworks(properties, modules)
    configureRouting()
}
