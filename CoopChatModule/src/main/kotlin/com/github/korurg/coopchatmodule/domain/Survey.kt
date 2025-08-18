package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.SurveyState
import com.github.korurg.coopchatmodule.domain.id.GameId
import com.github.korurg.coopchatmodule.domain.id.SurveyId
import com.github.korurg.telegrambot.domain.TelegramChat
import java.time.OffsetDateTime

data class Survey(
    val id: SurveyId,
    val state: SurveyState,
    val schedule: SurveySchedule,
    val chat: TelegramChat,
    val timePoll: Poll,
    val gamePoll: Poll?,
    val gameId: GameId? = null,
    val updated: OffsetDateTime = OffsetDateTime.now(),
    val created: OffsetDateTime = OffsetDateTime.now(),
)
