plugins {
    alias(libs.plugins.jvm)
}

dependencies {
    implementation(project(":TelegramAdapter"))
    implementation(libs.koin.coreJvm)

    testImplementation(kotlin("test"))
}
