package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.SteamAppCategory
import com.github.korurg.coopchatmodule.domain.SteamAppPrice
import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.coopchatmodule.domain.id.GameId
import com.github.korurg.coopchatmodule.domain.id.SteamAppId
import com.github.korurg.coopchatmodule.domain.id.SteamAppInternalId
import com.github.korurg.coopchatmodule.domain.id.SteamAppPriceId
import com.github.korurg.persistence.entity.tables.records.GameRecord
import com.github.korurg.persistence.entity.tables.records.SteamAppCategoryRecord
import com.github.korurg.persistence.entity.tables.records.SteamAppPriceRecord
import com.github.korurg.persistence.entity.tables.records.SteamAppRecord
import io.mcarle.konvert.api.Konvert
import io.mcarle.konvert.api.Konverter
import io.mcarle.konvert.api.Mapping
import io.mcarle.konvert.injector.spring.KComponent

@Konverter
@KComponent
interface GameMapper {

    fun toGameRecord(game: Game): GameRecord

    @Konvert(
        mappings = [
            Mapping(target = "watchPriceChanges", constant = "true"),
            Mapping(target = "watchPurchases", constant = "true"),
            Mapping(target = "watchForUpdates", constant = "true"),
        ]
    )
    fun toGame(gameRecord: GameRecord): Game

    @Konvert(
        mappings = [
            Mapping(target = "steamId", expression = "it.steamId.value") //bug
        ]
    )
    fun toSteamAppRecord(steamApp: SteamApp): SteamAppRecord

    @Konvert(
        mappings = [
            Mapping(
                target = "steamId",
                expression = "com.github.korurg.coopchatmodule.domain.id.SteamAppId(it.steamId)"
            )  //also bug
        ]
    )
    fun toSteamApp(steamAppRecord: SteamAppRecord): SteamApp

    fun gameIdToLong(value: GameId?): Long? {
        return value?.value
    }

    fun longToGameId(value: Long?): GameId? {
        return value?.let { GameId(it) }
    }

    fun steamAppInternalIdToLong(value: SteamAppInternalId?): Long? {
        return value?.value
    }

    fun longToSteamAppInternalId(value: Long?): SteamAppInternalId? {
        return value?.let { SteamAppInternalId(it) }
    }

    fun steamAppIdToLong(value: SteamAppId?): Long? {
        return value?.value
    }

    fun stringToFillState(value: String): SteamAppFillState {
        return SteamAppFillState.valueOf(value)
    }

    @Konvert(
        mappings = [
            Mapping(target = "steamAppId", expression = "it.steamAppId!!.value"),
        ]
    )
    fun toSteamAppPriceRecord(steamAppPrice: SteamAppPrice): SteamAppPriceRecord

    fun longToSteamAppPriceId(value: Long?): SteamAppPriceId? {
        return value?.let { SteamAppPriceId(it) }
    }

    fun steamAppPriceIdToLong(value: SteamAppPriceId?): Long? {
        return value?.value
    }

    fun toSteamAppPrice(steamAppPriceRecord: SteamAppPriceRecord): SteamAppPrice

    @Konvert(
        mappings = [
            Mapping(
                target = "steamCategoryId",
                expression = "com.github.korurg.coopchatmodule.domain.id.SteamCategoryId(it.steamId)"
            ),
            Mapping(
                target = "id",
                expression = "com.github.korurg.coopchatmodule.domain.id.SteamCategoryInternalId(it.id!!)"
            ),
        ]
    )
    fun toSteamAppCategory(stamAppCategoryRecord: SteamAppCategoryRecord): SteamAppCategory

    @Konvert(
        mappings = [
            Mapping(
                target = "id",
                expression = "it.id?.value"
            ),
            Mapping(
                target = "steamId",
                expression = "it.steamCategoryId.value"
            ),
        ]
    )
    fun toSteamAppCategoryRecord(steamAppCategory: SteamAppCategory): SteamAppCategoryRecord


}
