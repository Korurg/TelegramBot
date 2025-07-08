package com.github.korurg.persistenceadapter.repository

import com.github.korurg.persistence.entity.tables.records.GameRecord
import com.github.korurg.persistence.entity.tables.references.GAME
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class GameRepository(
    private val dslContext: DSLContext
) : AbstractJooqRepository<GameRecord, Long?>(
    dslContext,
    GAME,
    GAME.ID
) {
}
