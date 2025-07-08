package com.github.korurg.telegrambot.unit

import com.github.korurg.coopchatmodule.application.CommandParserService
import com.github.korurg.coopchatmodule.domain.BotCommandInfo
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class CommandParserServiceTests {

    @Test
    fun parseCommand_whenCommandWithArgument(){
        val commandText = "/gameadd Elden Ring Nightreign"

        val commandParserService = CommandParserService()

        val command = commandParserService.parse(commandText)

        assertThat(command.command).isEqualTo("gameadd")
        assertThat(command.argument).isEqualTo("Elden Ring Nightreign")
    }

    @Test
    fun parseCommand_whenCommandWithArgumentAndWithBotName(){
        val commandText = "/gameadd@KorurgPersonalBot Elden Ring Nightreign"

        val commandParserService = CommandParserService()

        val command = commandParserService.parse(commandText)

        assertThat(command.command).isEqualTo("gameadd")
        assertThat(command.botName).isEqualTo("KorurgPersonalBot")
        assertThat(command.argument).isEqualTo("Elden Ring Nightreign")
    }

    @Test
    fun parseCommand_whenCommandWithoutArgument(){
        val commandText = "/poolstart"

        val commandParserService = CommandParserService()

        val command = commandParserService.parse(commandText)

        assertThat(command.command).isEqualTo("poolstart")
    }
}
