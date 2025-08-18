package com.github.korurg.cooptelegrambot

import CoopChatModuleMessages
import com.github.korurg.coopchatmodule.config.Commands
import com.github.korurg.coopchatmodule.domain.id.SteamAppExternalId
import com.github.korurg.persistenceadapter.domain.entity.PollOptionEntity
import com.github.korurg.persistenceadapter.domain.entity.PollEntity
import com.github.korurg.persistenceadapter.domain.entity.SurveyEntity
import com.github.korurg.persistenceadapter.domain.entity.SurveyScheduleEntity
import com.github.korurg.persistenceadapter.mapper.toGameEntity
import com.github.korurg.telegrambot.domain.TelegramStartPollCommand
import com.github.korurg.telegrambot.domain.id.PollExternalId
import io.mockk.coEvery
import io.mockk.coVerify
import kotlin.test.Test

class StartPollTests : BaseTest() {

    @Test
    fun `start default poll in chat by command`() = testApplicationWithDb {
        //given
        val game1 = defaultGame(SteamAppExternalId(1L))
            .copy(
                name = "game 1"
            )
        val game2 = defaultGame(SteamAppExternalId(2L))
            .copy(
                name = "game 2"
            )
        val game3 = defaultGame(SteamAppExternalId(3L))
            .copy(
                name = "game 3"
            )

        val game1Id = kSqlClient.save(game1.toGameEntity()).modifiedEntity.id
        val game2Id = kSqlClient.save(game2.toGameEntity()).modifiedEntity.id
        val game3Id = kSqlClient.save(game3.toGameEntity()).modifiedEntity.id

        val messageStartPoll = defaultMessage("/${Commands.POLL_START}")

        val startGamePollCommand = TelegramStartPollCommand(
            chatId = defaultChatExternalId(),
            question = CoopChatModuleMessages.gamePollQuestion(),
            answers = listOf(
                "[${game1Id}] ${game1.name}",
                "[${game2Id}] ${game2.name}",
                "[${game3Id}] ${game3.name}",
            ),
            isAnonymous = false,
        )

        val startTimePollCommand = TelegramStartPollCommand(
            chatId = defaultChatExternalId(),
            question = CoopChatModuleMessages.timePollQuestion(),
            answers = listOf(
                CoopChatModuleMessages.timePollWonAbleAnswer(),
                "21:00",
                "20:30",
                "20:00",
                "19:30",
                "19:00",
            ),
            isAnonymous = false,
        )

        val gamePollId = PollExternalId("1")
        val timePollId = PollExternalId("2")

        coEvery { telegramBotOutAdapter.startPoll(startGamePollCommand) } returns gamePollId
        coEvery { telegramBotOutAdapter.startPoll(startTimePollCommand) } returns timePollId


        //when
        telegramMessageReceiveUseCase.receiveMessage(messageStartPoll)

        //TODO: emulate users

        //then
        coVerify { telegramBotOutAdapter.startPoll(startGamePollCommand) }
        coVerify { telegramBotOutAdapter.startPoll(startTimePollCommand) }

        assertThatTableRecordsCount(1, SurveyEntity::class, kSqlClient)
        assertThatTableRecordsCount(1, SurveyScheduleEntity::class, kSqlClient)
        assertThatTableRecordsCount(2, PollEntity::class, kSqlClient)
        assertThatTableRecordsCount(9, PollOptionEntity::class, kSqlClient)
    }
}
