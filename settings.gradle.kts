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
include("TelegramBotKtorApplication")
include("CoopChatModule")
include("TelegramBotCore")
include("TelegramAdapter")
include("SteamAdapter")

include("PersistenceAdapter")
include("PersistenceAdapterKoinModule")

include("TelegramAdapterKoinModule")
include("SteamAdapterKoinModule")
include("CoopChatModuleKoinModule")
include("TelegramBotCoreKoinModule")