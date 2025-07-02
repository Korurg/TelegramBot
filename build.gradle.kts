import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.palantir.git-version")
    id("org.springframework.boot") apply false
    kotlin("jvm") apply false
}

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val details = versionDetails()
val botVersion = providers.gradleProperty("bot-version").get().removeSurrounding("\"")
val computedVersion = "$botVersion-${details.branchName.split("/").last()}-${details.gitHash}"

extra["computedVersion"] = computedVersion

allprojects{
    group = "com.github.korurg"
    version = computedVersion
    println("DEBUG: $computedVersion")

    tasks.withType<Test>{
        useJUnitPlatform()
    }

    plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper> {
        configure<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension> {
            jvmToolchain(21)
        }
    }
}
