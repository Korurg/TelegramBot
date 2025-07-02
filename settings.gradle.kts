pluginManagement {
    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
        id("com.palantir.git-version") version "3.4.0"
        kotlin("jvm") version "2.2.0"
        id("org.springframework.boot") version "3.4.4"
        id("io.spring.dependency-management") version "1.1.7"
        kotlin("plugin.spring") version "2.2.0"

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
