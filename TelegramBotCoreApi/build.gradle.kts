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
    api("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.3.0")
    api("org.slf4j:slf4j-api:2.0.17")
    api("ch.qos.logback:logback-classic:1.5.18")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
