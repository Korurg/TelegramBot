package com.github.korurg.steamadapter.adapter.out

import com.github.korurg.coopchatmodule.application.port.`in`.SteamAppProviderPort
import com.github.korurg.coopchatmodule.domain.SteamApp
import com.github.korurg.coopchatmodule.domain.SteamAppCategory
import com.github.korurg.coopchatmodule.domain.SteamAppPrice
import com.github.korurg.coopchatmodule.domain.enums.SteamAppFillState
import com.github.korurg.coopchatmodule.domain.id.SteamAppId
import com.github.korurg.coopchatmodule.domain.id.SteamCategoryId
import com.github.korurg.ktsteam.KtSteamClient
import com.github.korurg.ktsteam.dto.AppDetail
import com.github.korurg.ktsteam.dto.AppDetailResult
import com.github.korurg.ktsteam.dto.AppId
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class SteamAppAdapter(
    private val ktSteamClient: KtSteamClient,
) : SteamAppProviderPort {
    private val logger = KotlinLogging.logger {}

    override fun findSteamAppBySteamId(steamId: SteamAppId): SteamApp? {
        val appDetailResult = runBlocking {
            ktSteamClient.steamAppDetails().getAppDetail(AppId(steamId.value))
        }

        return when (appDetailResult) {
            is AppDetailResult.Failed -> {
                null
            }
            is AppDetailResult.Success -> {
                toSteamApp(steamId, appDetailResult.appDetail)
            }
        }
    }
    //TODO: get version

    private fun toSteamApp(
        steamId: SteamAppId,
        appDetail: AppDetail
    ): SteamApp = SteamApp(
        steamId = steamId,
        name = appDetail.name,
        type = appDetail.type.name,
        fillState = SteamAppFillState.FILLED,
        version = null,
        description = appDetail.detailedDescription,
        shortDescription = appDetail.shortDescription,
        categories = appDetail.categories.map {
            SteamAppCategory(
                steamCategoryId = SteamCategoryId(it.id),
                description = it.description
            )
        },
        currentPrice = appDetail.priceOverview?.let {
            SteamAppPrice(
                currency = it.currency.name,
                initial = it.initial,
                final = it.final,
                discountPercent = it.discountPercent,
                initialFormatted = it.initialFormatted,
                finalFormatted = it.finalFormatted,
            )
        },
        id = null,
        updated = OffsetDateTime.now(),
        created = OffsetDateTime.now(),
    )
}
