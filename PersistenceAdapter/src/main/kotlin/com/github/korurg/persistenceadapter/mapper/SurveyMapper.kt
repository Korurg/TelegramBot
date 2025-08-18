package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.coopchatmodule.domain.Poll
import com.github.korurg.coopchatmodule.domain.PollAnswer
import com.github.korurg.coopchatmodule.domain.Survey
import com.github.korurg.coopchatmodule.domain.SurveySchedule
import com.github.korurg.persistenceadapter.domain.entity.PollAnswerEntity
import com.github.korurg.persistenceadapter.domain.entity.PollEntity
import com.github.korurg.persistenceadapter.domain.entity.SurveyEntity
import com.github.korurg.persistenceadapter.domain.entity.SurveyScheduleEntity


fun Survey.toSurveyEntity() = SurveyEntity {
    this@toSurveyEntity.id?.let {
        id = it.value
    }
    chatId = requireNotNull(this@toSurveyEntity.chat.id).value
    state = this@toSurveyEntity.state.name
    schedule = this@toSurveyEntity.schedule.toSurveyScheduleEntity()
    gamePoll = this@toSurveyEntity.gamePoll?.toPollEntity()
    timePoll = this@toSurveyEntity.timePoll.toPollEntity()
}


fun SurveySchedule.toSurveyScheduleEntity() = SurveyScheduleEntity {
    this@toSurveyScheduleEntity.id?.let {
        id = it.value
    }
    startDate = this@toSurveyScheduleEntity.startDate
    endDate = this@toSurveyScheduleEntity.endDate
    type = this@toSurveyScheduleEntity.type.name
    intervalCron = this@toSurveyScheduleEntity.intervalCron
    skipUntil = this@toSurveyScheduleEntity.skipUntil
    timezone = this@toSurveyScheduleEntity.timezone
}


fun Poll.toPollEntity() = PollEntity {
    this@toPollEntity.id?.let {
        id = it.value
    }
    telegramId = this@toPollEntity.telegramId.value
    type = this@toPollEntity.type.name
    answers = this@toPollEntity.answers.map { it.toPollAnswerEntity() }
}

fun PollAnswer.toPollAnswerEntity() = PollAnswerEntity {
    id = this@toPollAnswerEntity.id.value
    telegramId = this@toPollAnswerEntity.pollAnswerId.value
    answer = this@toPollAnswerEntity.answer
    type = this@toPollAnswerEntity.type.name
    gameIdAnswer = this@toPollAnswerEntity.gameIdAnswer?.value
    timeAnswer = this@toPollAnswerEntity.timeAnswer
}
