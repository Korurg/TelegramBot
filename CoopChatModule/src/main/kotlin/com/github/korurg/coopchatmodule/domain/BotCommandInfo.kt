package com.github.korurg.coopchatmodule.domain

data class BotCommandInfo(
    val command: String,
    val description: String?,
    val example: String?,
    val params: List<BotCommandParamInfo>,
)

data class BotCommandParamInfo(
    val name: String,
    val shortName: String?,
    val description: String?,
    val defaultValue: String?,
)
