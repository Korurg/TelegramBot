plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    implementation(libs.telegram.bot) {
        //FIXME: https://github.com/kotlin-telegram-bot/kotlin-telegram-bot/issues/319
        exclude("com.github.kotlin-telegram-bot.kotlin-telegram-bot", "webhook")
    }


    api(project(":CoopChatModule"))
    api(project(":TelegramBotCore"))


    testImplementation(kotlin("test"))
}
