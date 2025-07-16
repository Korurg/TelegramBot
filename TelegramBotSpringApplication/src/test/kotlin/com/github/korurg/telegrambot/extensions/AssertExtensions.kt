package com.github.korurg.telegrambot.extensions

import org.assertj.core.api.ObjectAssert

fun <T> ObjectAssert<T>.isNotNullKt() {

    this.isNotNull
}
