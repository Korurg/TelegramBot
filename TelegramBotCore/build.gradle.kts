plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    implementation(libs.reflections)
    api(libs.logging)
    api(libs.coroutines)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
