pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
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

include("TelegramBotSpringApplication")
include("CoopChatModule")
include("TelegramBotCore")
include("TelegramAdapter")
include("PersistenceAdapter")

include("SteamAdapter")
