plugins {
    alias(libs.plugins.jvm)
}

dependencies {
    implementation(project(":PersistenceAdapter"))
    implementation(libs.koin.coreJvm)

    testImplementation(kotlin("test"))
}
