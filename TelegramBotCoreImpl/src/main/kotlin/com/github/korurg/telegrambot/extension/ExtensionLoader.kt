package com.github.korurg.telegrambot.extension

import com.github.korurg.telegrambot.api.config.logger
import com.github.korurg.telegrambot.api.extension.TelegramBotExtension
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import java.io.File
import java.net.URLClassLoader
import java.util.jar.JarFile

class ExtensionLoader(val extensionPath: String) {

    private val log = logger()

    fun loadExtensions(): List<TelegramBotExtension> {
        val directory = File(extensionPath)

        val jarFiles = (directory.listFiles()
            ?.filter { it.isFile }
            ?.filter { it.extension == "jar" }
            ?: emptyList())

        val classLoader = URLClassLoader(jarFiles.map { it.toURI().toURL() }.toTypedArray(), javaClass.classLoader)

        val loadedClasses: MutableMap<String, Class<*>> = HashMap()

        jarFiles.forEach { jarFile ->
            JarFile(jarFile).use { jar ->
                val classes = jar.entries()
                    .asSequence()
                    .filter { it.name.endsWith(".class") }
                    .mapNotNull { entry ->
                        try {
                            val className = entry.name
                                .removeSuffix(".class")
                                .replace('/', '.')
                            className to classLoader.loadClass(className)
                        } catch (e: ClassNotFoundException) {
                            null
                        } catch (e: NoClassDefFoundError) {
                            null
                        }
                    }
                    .toMap()
                loadedClasses.putAll(classes)
            }
        }

        val reflections = Reflections(
            ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClassLoader(classLoader))
                .addClassLoaders(classLoader)
                .setScanners(Scanners.SubTypes)
        )

        val extensions = reflections.getSubTypesOf(TelegramBotExtension::class.java).map {
            it.getDeclaredConstructor().newInstance()
        }

        log.info("Extensions loaded ${extensions.count()}")

        extensions.forEach { extension ->
            log.info("Extension name: ${extension.getExtensionName()}; version: ${extension.getVersion()}; author: ${extension.getAuthor()}")
        }

        return extensions;
    }
}
