package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.coopchatmodule.application.port.out.SurveyCommandPort
import com.github.korurg.coopchatmodule.domain.Survey
import com.github.korurg.persistenceadapter.mapper.toSurveyEntity
import org.babyfish.jimmer.sql.kt.KSqlClient

class SurveyAdapter(
    private val kSqlClient: KSqlClient
) : SurveyCommandPort {
    override suspend fun saveSurvey(survey: Survey) {
        kSqlClient.transaction {

            kSqlClient.save(survey.toSurveyEntity())

        }
    }
}
