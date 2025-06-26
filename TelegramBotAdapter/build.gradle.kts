plugins {
    `java-library`
    kotlin("jvm")
}

group = "com.github.korurg"
version = "unspecified"

dependencies {
    api(libs.telegram.bot)
    api(project(":TelegramBotCoreApi"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
