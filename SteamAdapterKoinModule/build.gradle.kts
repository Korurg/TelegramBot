plugins {
    alias(libs.plugins.jvm)
}


dependencies {
    implementation(project(":SteamAdapter"))
    implementation(libs.koin.coreJvm)

    testImplementation(kotlin("test"))
}
