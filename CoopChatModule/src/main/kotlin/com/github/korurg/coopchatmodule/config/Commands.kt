package com.github.korurg.coopchatmodule.config

import com.github.korurg.coopchatmodule.domain.BotCommandInfo

class Commands private constructor() {
    companion object {

        const val GAME_ADD = "gameadd"

        val COMMANDS = listOf(
            BotCommandInfo(
                command = "/$GAME_ADD",
                description = "Add game",
                example = "/$GAME_ADD Elden Ring Nightreign",
                params = listOf()
            )
        )
    }
}
