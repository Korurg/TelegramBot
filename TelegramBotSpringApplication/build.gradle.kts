plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.spring.plugin)
    alias(libs.plugins.spring.springBoot)
    alias(libs.plugins.spring.dependencyManagment)
}


val mockitoAgent by configurations.creating

dependencies {
    implementation(project(":TelegramBotCore"))
    implementation(project(":TelegramAdapter"))
    implementation(project(":CoopChatModule"))
    implementation(project(":PersistenceAdapter"))
    implementation(project(":SteamAdapter"))


    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(libs.bundles.logging)

    mockitoAgent("org.mockito:mockito-core") {
        isTransitive = false
    }


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks {
    getByName<Jar>("jar") {
        enabled = false
    }

    test {
        jvmArgs("-javaagent:${mockitoAgent.asPath}")
    }
}
