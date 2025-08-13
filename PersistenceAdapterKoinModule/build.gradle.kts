plugins {
    alias(libs.plugins.jvm)
}

dependencies {
    api(project(":PersistenceAdapter"))
    implementation(libs.koin.coreJvm)

    testImplementation(kotlin("test"))
}
