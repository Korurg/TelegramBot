package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.coopchatmodule.application.port.out.GameSavePort
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.persistence.entity.tables.records.GameRecord
import com.github.korurg.persistenceadapter.repository.GameRepository
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class GameAdapter(
    private val gameRepository: GameRepository
) : GameSavePort {
    override fun sageGame(game: Game) {
        gameRepository.save(
            GameRecord(
                id = null,
                name = game.name,
                minPlayers = game.minPlayers,
                maxPlayers = game.maxPlayers,
                updated = OffsetDateTime.now(),
                created = OffsetDateTime.now(),
            )
        )
    }
}
