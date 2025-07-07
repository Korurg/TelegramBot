import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property

plugins {
    `java-library`
    kotlin("jvm")
    kotlin("plugin.spring")
    id("nu.studer.jooq") version "8.2"
}

dependencies {
    api(project(":CoopChatModule"))
    api(project(":TelegramBotCore"))
    api(libs.bundles.db)

    jooqGenerator(libs.jooqMetaExtensions)
    jooqGenerator(libs.postgresql)

    implementation(libs.springboot.starter)
    compileOnly(libs.springboot.configurationProcessor)
    annotationProcessor(libs.springboot.configurationProcessor)

    testImplementation(kotlin("test"))


}

jooq {
    version.set(libs.versions.jooq)
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)

            jooqConfiguration.apply {
                logging = Logging.WARN

                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"

                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"

                        properties.addAll(
                            listOf(
                                Property().withKey("scripts").withValue("src/main/resources/db/schema.sql"),

                                Property().withKey("defaultNameCase").withValue("lower"),
                                Property().withKey("ignoreUnsigned").withValue("true"),

                                Property().withKey("jdbc.url").withValue("jdbc:postgresql://localhost:5432/dummy"),
                                Property().withKey("jdbc.user").withValue("dummy"),
                                Property().withKey("jdbc.password").withValue("dummy")
                            )
                        )
                    }

                    database.withExcludes("flyway_schema_history|databasechangelog.*")

                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = false
                        isFluentSetters = true

                        isNullableAnnotation = false
                        isNonnullAnnotation = false
                        isKotlinNotNullPojoAttributes = true
                        isKotlinNotNullRecordAttributes = true
                        isKotlinNotNullInterfaceAttributes = true
                    }

                    target.apply {
                        packageName = "com.github.korurg.persistence.entity"
                        directory = "build/generated-src/jooq/main"
                    }

                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }

            }


        }
    }
}

tasks {
    register("cleanJooq"){
        delete("build/generated-src/jooq")
    }

    clean {
        dependsOn("cleanJooq")
    }

//    compileJava {
//        dependsOn("generateJooq")
//    }
//
//    compileKotlin {
//        dependsOn("generateJooq")
//    }
}
