plugins {
    `java-library`
    alias(libs.plugins.jvm)
    alias(libs.plugins.i18n4k)
}

dependencies {
    api(project(":TelegramBotCore"))
    testImplementation(kotlin("test"))
}
