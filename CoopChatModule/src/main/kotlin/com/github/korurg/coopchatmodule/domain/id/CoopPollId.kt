package com.github.korurg.coopchatmodule.domain.id

import java.util.UUID

@JvmInline
value class CoopPollId(val value: UUID) {
    companion object {
        fun random(): CoopPollId = CoopPollId(UUID.randomUUID())
    }
}
