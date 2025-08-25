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
import io.kotest.core.spec.style.FreeSpec
import io.mockk.coEvery
import io.mockk.coVerify

class StartPollTests : FreeSpec({
    "start default poll" - {
        "4 users and 3 games" - {
            "one user change decision" - {
                "survey complete successful" {
                    testApplicationWithDb {
                        //given
                        val users = saveAll {
                            defaultUsers(4).map { it.toEntity() }
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

                        suspend fun pollGameAndTime(user: TelegramUser, timeAnswer: Int, gameAnswer: Int) {
                            telegramMessageReceiveUseCase.receivePollAnswer(
                                TelegramPollAnswer(
                                    pollTelegramId = timePollId,
                                    pollOptionTelegramId = PollOptionTelegramId(timeAnswer),
                                    user = user
                                )
                            )
                            telegramMessageReceiveUseCase.receivePollAnswer(
                                TelegramPollAnswer(
                                    pollTelegramId = gamePollId,
                                    pollOptionTelegramId = PollOptionTelegramId(gameAnswer),
                                    user = user
                                )
                            )

                        }


                        //when
                        telegramMessageReceiveUseCase.receiveMessage(messageStartPoll)


                        //first decisions
//                        pollGameAndTime(users[0], 1, 1)
//                        pollGameAndTime(users[1], 2, 2)
//                        pollGameAndTime(users[2], 2, 1)
//                        pollGameAndTime(users[4], 4, 1)

                        //user change time and game
//                        pollGameAndTime(users[1], 4, 1)

                        //then
                        coVerify { telegramBotOutAdapter.startPoll(startGamePollCommand) }
                        coVerify { telegramBotOutAdapter.startPoll(startTimePollCommand) }

                        SurveyEntity::class tableShouldBeSize 1
                        CoopPollEntity::class tableShouldBeSize 2
                        CoopPollOptionEntity::class tableShouldBeSize 9
                        TelegramPollEntity::class tableShouldBeSize 2
                        TelegramPollOptionEntity::class tableShouldBeSize 9
                    }
                }
            }

        }
    }
})
