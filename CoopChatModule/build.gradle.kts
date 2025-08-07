plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(project(":TelegramBotCore"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
