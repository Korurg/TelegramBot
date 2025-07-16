package com.github.korurg.telegramadapter

import org.springframework.stereotype.Component

@Component
class HtmlToTelegramMessageFormatter {

    private val replaces = mapOf(
        Pair("<h1>", "**"),
        Pair("</h1>", "**"),
        Pair("<p>", "\t"),
        Pair("</p>", ""),
        Pair("<br>", "\n"),
    )

    fun format(originalText: String): String {
        var result = originalText
        replaces.forEach { tag, replace ->
            result = result.replace(tag, replace)
        }

        return result
    }
}
