package com.github.korurg.coopchatmodule.application

import CoopChatModuleMessages
import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.out.SurveyCommandPort
import com.github.korurg.coopchatmodule.domain.*
import com.github.korurg.coopchatmodule.domain.enums.AnswerType
import com.github.korurg.coopchatmodule.domain.enums.PollType
import com.github.korurg.coopchatmodule.domain.enums.SurveyScheduleType
import com.github.korurg.coopchatmodule.domain.enums.SurveyState
import com.github.korurg.coopchatmodule.domain.id.PollOptionId
import com.github.korurg.coopchatmodule.domain.id.SurveyId
import com.github.korurg.telegrambot.application.port.`in`.TelegramChatQueryPort
import com.github.korurg.telegrambot.application.port.out.TelegramCommandPort
import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.id.ChatExternalId
import com.github.korurg.telegrambot.domain.id.PollOptionExternalId
import java.time.*
import java.time.format.DateTimeFormatter

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
                answers = timePollAnswers.map { it.optionText },
                isAnonymous = false,
            )
        )

        return Poll(
            telegramId = timePollId,
            type = PollType.TIME,
            options = timePollAnswers
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
                answers = gamePollAnswers.map { it.optionText },
                isAnonymous = false,
            )
        )

        return Poll(
            telegramId = gamePollId,
            type = PollType.GAME,
            options = gamePollAnswers,
        )
    }


    private fun createGamePollAnswers(games: List<Game>): List<PollOption> {
        var externalId = 0
        return games.map {
            requireNotNull(it.id)
            PollOption(
                id = PollOptionId.random(),
                optionText = "[${it.id.value}] ${it.name}",
                type = AnswerType.GAME,
                gameIdOption = it.id,
                timeOption = null,
                telegramId = PollOptionExternalId(externalId++),
            )
        }
    }

    private fun createTimePollAnswers(): List<PollOption> {
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
        val resultList = mutableListOf<PollOption>()

        resultList.add(
            PollOption(
                id = PollOptionId.random(),
                optionText = CoopChatModuleMessages.timePollWonAbleAnswer(),
                type = AnswerType.TIME,
                gameIdOption = null,
                timeOption = null,
                telegramId = PollOptionExternalId(externalId++)
            )
        )
        resultList.addAll(offsetDateTimes.reversed().map {
            PollOption(
                id = PollOptionId.random(),
                optionText = it.format(DateTimeFormatter.ofPattern("HH:mm")),
                type = AnswerType.TIME,
                gameIdOption = null,
                timeOption = it,
                telegramId = PollOptionExternalId(externalId++),
            )
        })

        return resultList
    }

}
