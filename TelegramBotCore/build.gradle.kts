plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    implementation(libs.reflections)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
