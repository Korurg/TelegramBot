package com.github.korurg.cooptelegrambot

import CoopChatModuleMessages
import com.github.korurg.coopchatmodule.config.Commands
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId
import com.github.korurg.persistenceadapter.domain.entity.*
import com.github.korurg.persistenceadapter.mapper.toDomain
import com.github.korurg.persistenceadapter.mapper.toEntity
import com.github.korurg.persistenceadapter.mapper.toGame
import com.github.korurg.telegrambot.domain.TelegramPollAnswer
import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.TelegramUser
import com.github.korurg.telegrambot.domain.enums.PollType
import com.github.korurg.telegrambot.domain.id.PollOptionTelegramId
import com.github.korurg.telegrambot.domain.id.PollTelegramId
import com.github.korurg.telegrambot.domain.id.UserTelegramId
import io.mockk.coEvery
import io.mockk.coVerify
import kotlin.test.Test

class StartPollTests : BaseTest() {

    @Test
    fun `start default poll in chat by command`() = testApplicationWithDb {
        //given
        val users = saveAll {
            listOf(
                UserTelegramId(1L),
                UserTelegramId(2L),
                UserTelegramId(3L),
                UserTelegramId(4L),
            ).mapIndexed { index, id ->
                defaultTelegramUser(id).copy(
                    firstName = "firstName$index",
                    lastName = "lastName$index",
                    username = "username$index",
                ).toEntity()
            }
        }.map { it.toDomain() }

        val games = saveAll {
            listOf(
                defaultGame(SteamAppExternalId(1L))
                    .copy(
                        name = "game 1"
                    ),
                defaultGame(SteamAppExternalId(2L))
                    .copy(
                        name = "game 2"
                    ),
                defaultGame(SteamAppExternalId(3L))
                    .copy(
                        name = "game 3"
                    )
            ).map { it.toEntity() }
        }.map { it.toGame() }

        val messageStartPoll = defaultMessage("/${Commands.POLL_START}")

        val startGamePollCommand = TelegramStartPollCommand(
            chatTelegramId = defaultChatExternalId(),
            question = CoopChatModuleMessages.gamePollQuestion(),
            options = listOf(
                "[${games[0].id!!.value}] ${games[0].name}",
                "[${games[1].id!!.value}] ${games[1].name}",
                "[${games[2].id!!.value}] ${games[2].name}",
            ),
            isAnonymous = false,
            type = PollType.REGULAR,
        )

        val startTimePollCommand = TelegramStartPollCommand(
            chatTelegramId = defaultChatExternalId(),
            question = CoopChatModuleMessages.timePollQuestion(),
            options = listOf(
                CoopChatModuleMessages.timePollWonAbleAnswer(),
                "21:00",
                "20:30",
                "20:00",
                "19:30",
                "19:00",
            ),
            isAnonymous = false,
            type = PollType.REGULAR,
        )

        val gamePollId = PollTelegramId("1")
        val timePollId = PollTelegramId("2")

        coEvery { telegramBotOutAdapter.startPoll(startGamePollCommand) } returns gamePollId
        coEvery { telegramBotOutAdapter.startPoll(startTimePollCommand) } returns timePollId


        //when
        telegramMessageReceiveUseCase.receiveMessage(messageStartPoll)


        suspend fun pollGameAndTime(user: TelegramUser, timeAnswer: Int, gameAnswer: Int) {
            telegramMessageReceiveUseCase.receivePollAnswer(
                TelegramPollAnswer(
                    pollId = timePollId,
                    answersIds = listOf(PollOptionTelegramId(timeAnswer)),
                    user = user
                )
            )
            telegramMessageReceiveUseCase.receivePollAnswer(
                TelegramPollAnswer(
                    pollId = gamePollId,
                    answersIds = listOf(PollOptionTelegramId(gameAnswer)),
                    user = user
                )
            )
        }

        pollGameAndTime(users[0], 1, 1)



        //then
        coVerify { telegramBotOutAdapter.startPoll(startGamePollCommand) }
        coVerify { telegramBotOutAdapter.startPoll(startTimePollCommand) }

        assertThatTableRecordsCount(1, SurveyEntity::class, kSqlClient)
        assertThatTableRecordsCount(1, SurveyScheduleEntity::class, kSqlClient)
        assertThatTableRecordsCount(2, CoopPollEntity::class, kSqlClient)
        assertThatTableRecordsCount(9, CoopPollOptionEntity::class, kSqlClient)
        assertThatTableRecordsCount(2, TelegramPollEntity::class, kSqlClient)
        assertThatTableRecordsCount(9, TelegramPollOptionEntity::class, kSqlClient)
    }
}
