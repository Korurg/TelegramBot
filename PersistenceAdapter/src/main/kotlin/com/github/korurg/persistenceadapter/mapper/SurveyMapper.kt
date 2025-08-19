package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.coopchatmodule.domain.CoopPoll
import com.github.korurg.coopchatmodule.domain.CoopPollOption
import com.github.korurg.coopchatmodule.domain.Survey
import com.github.korurg.coopchatmodule.domain.SurveySchedule
import com.github.korurg.persistenceadapter.domain.entity.CoopPollEntity
import com.github.korurg.persistenceadapter.domain.entity.CoopPollOptionEntity
import com.github.korurg.persistenceadapter.domain.entity.SurveyEntity
import com.github.korurg.persistenceadapter.domain.entity.SurveyScheduleEntity


fun Survey.toEntity() = SurveyEntity {
    id = this@toEntity.id.value
    chatId = requireNotNull(this@toEntity.chat.id).value
    state = this@toEntity.state.name
    schedule = this@toEntity.schedule.toEntity()
    gamePoll = this@toEntity.gameCoopPoll?.toEntity()
    timePoll = this@toEntity.timeCoopPoll.toEntity()
}


fun SurveySchedule.toEntity() = SurveyScheduleEntity {
    this@toEntity.id?.let {
        id = it.value
    }
    startDate = this@toEntity.startDate
    endDate = this@toEntity.endDate
    type = this@toEntity.type.name
    intervalCron = this@toEntity.intervalCron
    skipUntil = this@toEntity.skipUntil
    timezone = this@toEntity.timezone
}


fun CoopPoll.toEntity() = CoopPollEntity {
    id = this@toEntity.id.value
    poll = this@toEntity.poll.toEntity()
    type = this@toEntity.type.name
    options = this@toEntity.options.map { it.toEntity() }
}

fun CoopPollOption.toEntity() = CoopPollOptionEntity {
    id = this@toEntity.id.value
    type = this@toEntity.type.name
    gameIdOption = this@toEntity.gameIdOption?.value
    timeOption = this@toEntity.timeOption
    option = this@toEntity.pollOption.toEntity()
}
