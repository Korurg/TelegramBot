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
    implementation("org.reflections:reflections:0.10.2")
    api(project(":TelegramBotCoreApi"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
