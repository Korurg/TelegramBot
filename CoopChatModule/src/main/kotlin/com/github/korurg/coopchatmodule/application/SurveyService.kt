package com.github.korurg.coopchatmodule.application

import CoopChatModuleMessages
import com.github.korurg.coopchatmodule.application.port.`in`.GameQueryPort
import com.github.korurg.coopchatmodule.application.port.out.SurveyCommandPort
import com.github.korurg.coopchatmodule.domain.*
import com.github.korurg.coopchatmodule.domain.enums.AnswerType
import com.github.korurg.coopchatmodule.domain.enums.CoopPollType
import com.github.korurg.coopchatmodule.domain.enums.SurveyScheduleType
import com.github.korurg.coopchatmodule.domain.enums.SurveyState
import com.github.korurg.coopchatmodule.domain.id.CoopPollId
import com.github.korurg.coopchatmodule.domain.id.CoopPollOptionId
import com.github.korurg.coopchatmodule.domain.id.GameId
import com.github.korurg.coopchatmodule.domain.id.SurveyId
import com.github.korurg.telegrambot.application.TelegramPollService
import com.github.korurg.telegrambot.application.port.`in`.TelegramChatQueryPort
import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.id.ChatTelegramId
import java.time.*
import java.time.format.DateTimeFormatter

private const val DEFAULT_START_POLL_TIME = "0 17 * * *"
private const val DEFAULT_TIMEZONE = "Europe/Moscow"

class SurveyService(
    private val gameQueryPort: GameQueryPort,
    private val chatQueryPort: TelegramChatQueryPort,
    private val telegramPollService: TelegramPollService,
    private val surveyCommandPort: SurveyCommandPort,
) {
    suspend fun startDefaultPoll(telegramId: ChatTelegramId) {
        val topActiveGames = gameQueryPort.getTopActiveGames(5)
        startDefaultPoll(telegramId, topActiveGames)
    }

    suspend fun startDefaultPoll(chatId: ChatTelegramId, games: List<Game>) {
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
            timeCoopPoll = timePoll,
            gameCoopPoll = gamePoll,
            gameId = game?.id
        )

        surveyCommandPort.saveSurvey(survey)
    }

    private suspend fun startTimePoll(
        chatId: ChatTelegramId,
        game: Game?
    ): CoopPoll {
        val timePollOptions = createTimePollOptions()

        val question = game?.let {
            CoopChatModuleMessages.timePollWithConcreteGameQuestion(it.name)
        } ?: CoopChatModuleMessages.timePollQuestion()

        val timePoll = telegramPollService.startPoll(
            TelegramStartPollCommand(
                chatTelegramId = chatId,
                question = question,
                options = timePollOptions.keys.toList(),
                isAnonymous = false,
            )
        )
        //TODO: refactor
        return CoopPoll(
            id = CoopPollId.random(),
            poll = timePoll,
            type = CoopPollType.TIME,
            options = timePoll.options.map {
                CoopPollOption(
                    id = CoopPollOptionId.random(),
                    pollOption = it,
                    type = AnswerType.TIME,
                    gameIdOption = null,
                    timeOption = timePollOptions[it.option]
                )
            }
        )
    }

    private suspend fun startGamePoll(chatId: ChatTelegramId, games: List<Game>): CoopPoll? {
        if (games.size <= 1) {
            return null
        }
        val gameOptions = createGamePollOptions(games)

        val gamePoll = telegramPollService.startPoll(
            TelegramStartPollCommand(
                chatTelegramId = chatId,
                question = CoopChatModuleMessages.gamePollQuestion(),
                options = gameOptions.keys.toList(),
                isAnonymous = false,
            )
        )

        return CoopPoll(
            id = CoopPollId.random(),
            poll = gamePoll,
            type = CoopPollType.GAME,
            options = gamePoll.options.map {
                CoopPollOption(
                    id = CoopPollOptionId.random(),
                    pollOption = it,
                    type = AnswerType.GAME,
                    gameIdOption = gameOptions[it.option],
                    timeOption = null
                )
            },
        )
    }


    private fun createGamePollOptions(games: List<Game>): Map<String, GameId> {
        return games.associate {
            requireNotNull(it.id)
            "[${it.id.value}] ${it.name}" to it.id
        }
    }

    private fun createTimePollOptions(): Map<String, OffsetDateTime?> {
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

        val resultList = mutableMapOf<String, OffsetDateTime?>()

        resultList[CoopChatModuleMessages.timePollWonAbleAnswer()] = null
        offsetDateTimes.reversed().forEach {
            resultList[it.format(DateTimeFormatter.ofPattern("HH:mm"))] = it
        }

        return resultList
    }

}
