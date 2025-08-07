plugins {
    alias(libs.plugins.jvm)
}


dependencies {
    implementation(project(":CoopChatModule"))
    implementation(libs.koin.coreJvm)

    testImplementation(kotlin("test"))
}
