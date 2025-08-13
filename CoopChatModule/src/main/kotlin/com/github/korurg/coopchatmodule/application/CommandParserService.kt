package com.github.korurg.coopchatmodule.application

import com.github.korurg.coopchatmodule.domain.Command

class CommandParserService {

    fun parse(text: String): Command {
        val commandRegex = Regex("/([a-zA-Z]+)@?([a-zA-Z]+)?")

        val matchResult = commandRegex.find(text)

        requireNotNull(matchResult) {
            "Invalid command pattern"
        }

        val groupValues = matchResult.groupValues

        val commandPrefix = groupValues[0]

        val command = groupValues[1]
        val botName = if (groupValues.size > 2) {
            groupValues[2].ifEmpty {
                null
            }
        } else {
            null
        }

        var argument = text.replace("$commandPrefix", "")

        if (argument.startsWith(" ")) {
            argument = argument.substring(1)
        }

        return Command(
            command = command,
            botName = botName,
            params = mapOf(),
            argument = argument.ifEmpty { null }
        )
    }
}
