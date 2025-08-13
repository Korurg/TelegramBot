plugins {
    alias(libs.plugins.jvm)
}

dependencies {
    api(project(":TelegramBotCore"))
    implementation(libs.koin.coreJvm)

    testImplementation(kotlin("test"))
}
