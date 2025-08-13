plugins {
    alias(libs.plugins.jvm)
}


dependencies {
    api(project(":SteamAdapter"))
    implementation(libs.koin.coreJvm)

    testImplementation(kotlin("test"))
}
