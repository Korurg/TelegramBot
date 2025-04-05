plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "telegram-bot"
include("CoopChat")
include("TelegramBot")
include("TelegramBotCoreApi")
include("TelegramBotCoreImpl")
