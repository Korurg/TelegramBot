package com.github.korurg.cooptelegrambot.ktorapplication

import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.core.module.Module
import org.slf4j.bridge.SLF4JBridgeHandler
import java.util.logging.LogManager

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module(
    properties: Map<String, Any> = mapOf(),
    modules: List<Module> = listOf()
) {
    LogManager.getLogManager().reset();
    SLF4JBridgeHandler.install()

    configureSerialization()
    configureFrameworks(properties, modules)
    configureRouting()
}
