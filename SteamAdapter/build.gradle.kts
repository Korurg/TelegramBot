plugins {
    `java-library`
    alias(libs.plugins.jvm)
    alias(libs.plugins.spring.plugin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
}

dependencies {
    api(project(":CoopChatModule"))
    api(project(":TelegramBotCore"))

    implementation(libs.mapstruct.core)
    kapt(libs.mapstruct.processor)

    implementation(libs.konverter.api)
    ksp(libs.konverter.processor)

    api(libs.ktsteam)

    implementation(libs.springboot.starter)
    compileOnly(libs.springboot.configurationProcessor)
    annotationProcessor(libs.springboot.configurationProcessor)

    testImplementation(kotlin("test"))
}
