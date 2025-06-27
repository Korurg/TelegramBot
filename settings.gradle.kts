pluginManagement {
    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
        id("com.palantir.git-version") version "3.4.0"
        kotlin("jvm") version "2.2.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "telegram-bot"
include(":TelegramBot")
include(":CoopChatModuleImpl")
include(":TelegramBotCoreApi")
include(":TelegramBotCoreImpl")

include("TelegramBotAdapter")
