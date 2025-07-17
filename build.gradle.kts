import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.gitVersion)

    alias(libs.plugins.jvm) apply false
    alias(libs.plugins.spring.plugin) apply false
    alias(libs.plugins.spring.springBoot) apply false
    alias(libs.plugins.foojayResolverConvention) apply false
    alias(libs.plugins.spring.dependencyManagment) apply false
    alias(libs.plugins.jooq) apply false
    alias(libs.plugins.kapt) apply false
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
