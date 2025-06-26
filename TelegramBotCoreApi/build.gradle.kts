plugins {
    `java-library`
    kotlin("jvm")
}

group = "com.github.korurg"
version = "unspecified"

dependencies {
    api("org.slf4j:slf4j-api:2.0.17")
    api("ch.qos.logback:logback-classic:1.5.18")
    api("org.jooq:jooq:3.20.5")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
