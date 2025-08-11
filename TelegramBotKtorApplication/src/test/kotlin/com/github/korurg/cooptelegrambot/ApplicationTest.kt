package com.github.korurg.cooptelegrambot

import com.github.korurg.cooptelegrambot.ktorapplication.module
import io.ktor.server.testing.*
import org.testcontainers.Testcontainers
import org.testcontainers.containers.PostgreSQLContainer
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {


    @Test
    fun testRoot2() = testApplicationWithDb {


        assert(true)
    }

}
