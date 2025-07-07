plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    implementation(libs.reflections)
    api(libs.logging)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
