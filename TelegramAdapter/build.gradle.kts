plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    implementation(libs.telegram.bot)

    api(project(":CoopChatModule"))
    api(project(":TelegramBotCore"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
