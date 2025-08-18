plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    implementation(libs.reflections)
    api(libs.logging)
    api(libs.coroutines)
    api(libs.i18n4k.jvm)

    testImplementation(kotlin("test"))
}
