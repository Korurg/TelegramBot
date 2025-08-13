package com.github.korurg.persistenceadapter.mapper

import com.github.korurg.coopchatmodule.domain.Survey
import com.github.korurg.coopchatmodule.domain.SurveySchedule
import com.github.korurg.persistenceadapter.domain.entity.SurveyEntity
import com.github.korurg.persistenceadapter.domain.entity.SurveyScheduleEntity


fun Survey.toSurveyEntity() = SurveyEntity {
    this@toSurveyEntity.id?.let {
        id = it.value
    }
    state = this@toSurveyEntity.state.name
    schedule = this@toSurveyEntity.schedule.toSurveyScheduleEntity()
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
