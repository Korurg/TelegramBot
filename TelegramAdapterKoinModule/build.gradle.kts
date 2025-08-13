plugins {
    alias(libs.plugins.jvm)
}

dependencies {
    api(project(":TelegramAdapter"))
    implementation(libs.koin.coreJvm)

    testImplementation(kotlin("test"))
}
