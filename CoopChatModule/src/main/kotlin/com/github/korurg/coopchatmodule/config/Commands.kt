package com.github.korurg.coopchatmodule.config

import com.github.korurg.coopchatmodule.domain.BotCommandInfo

class Commands private constructor() {
    companion object {

        const val GAME_ADD = "gameadd"
        const val POLL_START = "pollstart"

        val COMMANDS = listOf(
            BotCommandInfo(
                command = "/$GAME_ADD",
                description = "Add game",
                example = "/$GAME_ADD Elden Ring Nightreign",
                params = listOf()
            ),
            BotCommandInfo(
                command = "/$POLL_START",
                description = "Start poll",
                example = "/$POLL_START",
                params = listOf()
            )
        )
    }
}
