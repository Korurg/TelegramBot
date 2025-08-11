plugins {
    `java-library`
    alias(libs.plugins.jvm)
}

dependencies {
    implementation(libs.telegram.bot) {
        //FIXME: https://github.com/kotlin-telegram-bot/kotlin-telegram-bot/issues/319
        exclude("com.github.kotlin-telegram-bot.kotlin-telegram-bot", "webhook")
        exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
    }
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")



    api(project(":CoopChatModule"))
    api(project(":TelegramBotCore"))

    testImplementation(kotlin("test"))
}
