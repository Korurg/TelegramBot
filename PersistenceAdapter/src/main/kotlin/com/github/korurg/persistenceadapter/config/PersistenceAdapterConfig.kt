package com.github.korurg.persistenceadapter.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.github.korurg.persistenceadapter")
class PersistenceAdapterConfig {

    @Bean
    fun defaultDbCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
