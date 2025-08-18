plugins {
    `java-library`

    alias(libs.plugins.jvm)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
}

dependencies {
    api(project(":CoopChatModule"))
    api(project(":TelegramBotCore"))

    //mapper
    implementation(libs.konverter.api)
    ksp(libs.konverter.processor)

    //db
    api(libs.bundles.db)

    //orm
    api(libs.jimmer.sqlKotlin)
    ksp(libs.jimmer.ksp)

    implementation(libs.bundles.logging)

    //test
    testImplementation(kotlin("test"))
}
