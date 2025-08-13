package com.github.korurg.coopchatmodule.application

import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.domain.Game
import com.github.korurg.telegrambot.application.port.`in`.TelegramChatQueryPort
import com.github.korurg.telegrambot.application.port.out.TelegramCommandPort
import com.github.korurg.telegrambot.domain.id.ChatExternalId

private const val DEFAULT_START_POLL_TIME = "0 17 * * *"
private const val DEFAULT_TIMEZONE = "Europe/Moscow"

class SurveyService(
    private val gameQueryPort: GameQueryPort,
    private val chatQueryPort: TelegramChatQueryPort,
    private val telegramCommandPort: TelegramCommandPort,
) {
    suspend fun startDefaultPoll(telegramId: ChatExternalId) {
        val topActiveGames = gameQueryPort.getTopActiveGames(5)
        startDefaultPoll(telegramId, topActiveGames)
    }

    suspend fun startDefaultPoll(telegramId: ChatExternalId, games: List<Game>) {
        val telegramChat = chatQueryPort.findChatByExternalId(telegramId)
        requireNotNull(telegramChat)

        //TODO: создать опрос в телеге



        //TODO: сохранить данные опроса



//        Survey(
//            state = SurveyState.ACTIVE,
//            schedule = SurveySchedule(
//                startDate = LocalDate.now(),
//                endDate = LocalDate.now(),
//                type = SurveyScheduleType.ONCE,
//                intervalCron = DEFAULT_START_POLL_TIME,
//                timezone = DEFAULT_TIMEZONE,
//            ),
//            chat = telegramChat,
//            currentTimePoll = Poll(
//                telegramId = TODO(),
//                type = TODO(),
//                answers = TODO(),
//                updated = TODO(),
//                created = TODO()
//            ),
//            currentGamePoll = Poll(
//                id = TODO(),
//                telegramId = TODO(),
//                type = TODO(),
//                answers = TODO(),
//                updated = TODO(),
//                created = TODO()
//            ),
//        )


    }


}
