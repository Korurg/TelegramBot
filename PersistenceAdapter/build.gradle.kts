plugins {
    `java-library`
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":CoopChatModule"))
    api(project(":TelegramBotCore"))

    implementation(libs.postgresql)
    implementation(libs.sqlite)
    implementation(libs.hikari)
    implementation(libs.jooq)
    implementation(libs.liquibase)
    implementation(libs.springboot.starter)

    compileOnly(libs.springboot.configurationprocessor)
    annotationProcessor(libs.springboot.configurationprocessor)

    testImplementation(kotlin("test"))
}
