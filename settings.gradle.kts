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
        google()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "telegram-bot"

include("TelegramBotKtorApplication")

include("CoopChatModule")
include("CoopChatModuleKoinModule")

include("TelegramBotCore")
include("TelegramBotCoreKoinModule")

include("PersistenceAdapter")
include("PersistenceAdapterKoinModule")

include("TelegramAdapter")
include("TelegramAdapterKoinModule")

include("SteamAdapter")
include("SteamAdapterKoinModule")
