plugins {
    alias(libs.plugins.jvm)
}

dependencies {
    implementation(project(":TelegramBotCore"))
    implementation(libs.koin.coreJvm)

    testImplementation(kotlin("test"))
}
