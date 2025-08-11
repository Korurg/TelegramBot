plugins {
    alias(libs.plugins.gitVersion)

    alias(libs.plugins.jvm) apply false
    alias(libs.plugins.foojayResolverConvention) apply false
    alias(libs.plugins.spring.dependencyManagment) apply false
    alias(libs.plugins.kapt) apply false
}

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val details = versionDetails()
val botVersion = providers.gradleProperty("bot-version").get().removeSurrounding("\"")
val computedVersion = "$botVersion-${details.branchName.split("/").last()}-${details.gitHash}"

extra["computedVersion"] = computedVersion

subprojects {
    group = "com.github.korurg.cooptelegrambot"
    version = computedVersion
    println("DEBUG: $computedVersion")

    tasks.withType<Test> {
        useJUnitPlatform()
        doFirst {
            println("Classpath for tests:")
            classpath.files.forEach { println(it) }
        }
    }

    plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper> {
        configure<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension> {
            jvmToolchain(21)
        }
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        dependencies {
            //TODO:  to libs
            add("testImplementation", "org.assertj:assertj-core:3.27.4")
            add("testImplementation", kotlin("test"))
        }

    }
}
