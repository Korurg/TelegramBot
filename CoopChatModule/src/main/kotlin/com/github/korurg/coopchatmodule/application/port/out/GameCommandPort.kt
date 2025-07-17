package com.github.korurg.coopchatmodule.application.port.out

import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.SteamApp

interface GameCommandPort {
    suspend fun saveGame(game: Game): Game

    suspend fun saveSteamApp(steamApp: SteamApp): SteamApp
}
