plugins {
    `java-library`
    kotlin("jvm")
    id("com.palantir.git-version")
}

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val details = versionDetails()
val botVersion = providers.gradleProperty("bot-version").get().removeSurrounding("\"")


group = "com.github.korurg"
version = "$botVersion-${details.branchName}-${details.gitHash}"

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
