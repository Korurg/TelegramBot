plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.github.korurg"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api("org.telegram:telegrambots-meta:8.2.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
