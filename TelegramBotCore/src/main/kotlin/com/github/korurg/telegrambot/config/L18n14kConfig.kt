package com.github.korurg.telegrambot.config

import de.comahe.i18n4k.config.I18n4kConfig
import de.comahe.i18n4k.config.I18n4kConfigDefault
import de.comahe.i18n4k.i18n4k
import java.util.*

class L18n14kConfig {

    fun defaultConfig(): I18n4kConfig {
        val i18n4kConfig = I18n4kConfigDefault()
        i18n4k = i18n4kConfig
        i18n4kConfig.locale = Locale("ru")
        return i18n4kConfig
    }
}
