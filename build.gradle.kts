allprojects{
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}


var modules = arrayOf(
    "CoopChat"
)

tasks.register("buildAndCopyModules") {

    for (module in modules){
        dependsOn(":$module:build")

        doLast {
            val sourceFile = project(":$module").tasks
                .getByName<Jar>("jar").archiveFile.get().asFile
            val targetDir = file("extensions/").apply { mkdirs() }

            copy {
                from(sourceFile)
                into(targetDir)
                rename { _ ->
                    "$module-${project(":$module").version}.jar"
                }
            }

            logger.lifecycle("Copied ${sourceFile.name} to ${targetDir.path}")
        }
    }


}
