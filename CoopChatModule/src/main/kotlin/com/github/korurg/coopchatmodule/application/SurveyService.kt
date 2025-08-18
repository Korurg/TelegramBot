package com.github.korurg.coopchatmodule.application

import CoopChatModuleMessages
import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.out.SurveyCommandPort
import com.github.korurg.coopchatmodule.domain.*
import com.github.korurg.coopchatmodule.domain.enums.AnswerType
import com.github.korurg.coopchatmodule.domain.enums.PollType
import com.github.korurg.coopchatmodule.domain.enums.SurveyScheduleType
import com.github.korurg.coopchatmodule.domain.enums.SurveyState
import com.github.korurg.coopchatmodule.domain.id.PollAnswerId
import com.github.korurg.coopchatmodule.domain.id.SurveyId
import com.github.korurg.telegrambot.application.port.`in`.TelegramChatQueryPort
import com.github.korurg.telegrambot.application.port.out.TelegramCommandPort
import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.id.ChatExternalId
import com.github.korurg.telegrambot.domain.id.PollAnswerExternalId
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.UUID

private const val DEFAULT_START_POLL_TIME = "0 17 * * *"
private const val DEFAULT_TIMEZONE = "Europe/Moscow"

class SurveyService(
    private val gameQueryPort: GameQueryPort,
    private val chatQueryPort: TelegramChatQueryPort,
    private val telegramCommandPort: TelegramCommandPort,
    private val surveyCommandPort: SurveyCommandPort,
) {
    suspend fun startDefaultPoll(telegramId: ChatExternalId) {
        val topActiveGames = gameQueryPort.getTopActiveGames(5)
        startDefaultPoll(telegramId, topActiveGames)
    }

    suspend fun startDefaultPoll(chatId: ChatExternalId, games: List<Game>) {
        val telegramChat = chatQueryPort.findChatByExternalId(chatId)
        requireNotNull(telegramChat)

        val gamePoll = startGamePoll(chatId, games)
        val game = if (games.size == 1) games.first() else null
        val timePoll = startTimePoll(chatId, game)

        val nowDate = LocalDate.now()

        val survey = Survey(
            id = SurveyId.random(),
            state = SurveyState.ACTIVE,
            schedule = SurveySchedule(
                startDate = nowDate,
                endDate = nowDate,
                type = SurveyScheduleType.ONCE,
                intervalCron = DEFAULT_START_POLL_TIME,
                timezone = DEFAULT_TIMEZONE,
            ),
            chat = telegramChat,
            timePoll = timePoll,
            gamePoll = gamePoll,
            gameId = game?.id
        )

        surveyCommandPort.saveSurvey(survey)
    }

    private suspend fun startTimePoll(
        chatId: ChatExternalId,
        game: Game?
    ): Poll {
        val timePollAnswers = createTimePollAnswers()

        var question = CoopChatModuleMessages.timePollQuestion()
        game?.let {
            question = CoopChatModuleMessages.timePollWithConcreteGameQuestion(it.name)
        }

        val timePollId = telegramCommandPort.startPoll(
            TelegramStartPollCommand(
                chatId = chatId,
                question = question,
                answers = timePollAnswers.map { it.answer },
                isAnonymous = false,
            )
        )

        return Poll(
            telegramId = timePollId,
            type = PollType.TIME,
            answers = timePollAnswers
        )
    }

    private suspend fun startGamePoll(chatId: ChatExternalId, games: List<Game>): Poll? {
        if (games.size <= 1) {
            return null
        }
        val gamePollAnswers = createGamePollAnswers(games)
        val gamePollId = telegramCommandPort.startPoll(
            TelegramStartPollCommand(
                chatId = chatId,
                question = CoopChatModuleMessages.gamePollQuestion(),
                answers = gamePollAnswers.map { it.answer },
                isAnonymous = false,
            )
        )

        return Poll(
            telegramId = gamePollId,
            type = PollType.GAME,
            answers = gamePollAnswers,
        )
    }


    private fun createGamePollAnswers(games: List<Game>): List<PollAnswer> {
        var externalId = 0
        return games.map {
            requireNotNull(it.id)
            PollAnswer(
                id = PollAnswerId.random(),
                answer = "[${it.id.value}] ${it.name}",
                type = AnswerType.GAME,
                gameIdAnswer = it.id,
                timeAnswer = null,
                pollAnswerId = PollAnswerExternalId(externalId++),
            )
        }
    }

    private fun createTimePollAnswers(): List<PollAnswer> {
        //TODO: to db
        val startTime = LocalTime.of(19, 0)
        val stepMinutes = 30L

        val offsetDateTimes = ArrayList<OffsetDateTime>()
        for (range in 0..4) {
            offsetDateTimes.add(
                OffsetDateTime.of(
                    LocalDate.now(),
                    startTime.plusMinutes(range * stepMinutes),
                    ZoneId.of(DEFAULT_TIMEZONE).rules.getOffset(Instant.now())
                )
            )
        }

        var externalId = 0
        val resultList = mutableListOf<PollAnswer>()

        resultList.add(
            PollAnswer(
                id = PollAnswerId.random(),
                answer = CoopChatModuleMessages.timePollWonAbleAnswer(),
                type = AnswerType.TIME,
                gameIdAnswer = null,
                timeAnswer = null,
                pollAnswerId = PollAnswerExternalId(externalId++)
            )
        )
        resultList.addAll(offsetDateTimes.reversed().map {
            PollAnswer(
                id = PollAnswerId.random(),
                answer = it.format(DateTimeFormatter.ofPattern("HH:mm")),
                type = AnswerType.TIME,
                gameIdAnswer = null,
                timeAnswer = it,
                pollAnswerId = PollAnswerExternalId(externalId++),
            )
        })

        return resultList
    }

}
