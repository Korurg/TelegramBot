plugins {
    `java-library`
    kotlin("jvm") version "2.0.21"
}

group = "com.github.korurg"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":TelegramBotCoreApi"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
