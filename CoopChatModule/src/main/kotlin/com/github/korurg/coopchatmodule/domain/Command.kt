package com.github.korurg.coopchatmodule.domain

data class Command(
    val command: String,
    val botName: String?,
    val params: Map<String, String>,
    val argument: String?
)
