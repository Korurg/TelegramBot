package com.github.korurg.coopchatmodule.domain.id

import java.util.UUID

@JvmInline
value class CoopPollOptionId(val value: UUID) {
    companion object {
        fun random(): CoopPollOptionId = CoopPollOptionId(UUID.randomUUID())
    }
}
