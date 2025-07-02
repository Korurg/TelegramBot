plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    implementation(project(":TelegramBotCore"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
