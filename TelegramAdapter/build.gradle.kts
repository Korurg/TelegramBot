plugins {
    `java-library`
    kotlin("plugin.spring")
    kotlin("jvm")
}

dependencies {
    implementation(libs.telegram.bot) {
        //FIXME: https://github.com/kotlin-telegram-bot/kotlin-telegram-bot/issues/319
        exclude("com.github.kotlin-telegram-bot.kotlin-telegram-bot", "webhook")
    }


    api(project(":CoopChatModule"))
    api(project(":TelegramBotCore"))


    implementation(libs.springboot.starter)
    compileOnly(libs.springboot.configurationProcessor)
    annotationProcessor(libs.springboot.configurationProcessor)

    testImplementation(kotlin("test"))
}
