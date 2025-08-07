plugins {
    `java-library`
    alias(libs.plugins.jvm)
}

dependencies {
    api(project(":CoopChatModule"))
    api(project(":TelegramBotCore"))

    api(libs.ktsteam)

    testImplementation(kotlin("test"))
}
