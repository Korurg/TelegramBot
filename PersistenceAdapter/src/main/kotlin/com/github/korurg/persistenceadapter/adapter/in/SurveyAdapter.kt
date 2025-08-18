package com.github.korurg.persistenceadapter.adapter.`in`

import com.github.korurg.coopchatmodule.application.port.out.SurveyCommandPort
import com.github.korurg.coopchatmodule.domain.Survey
import org.babyfish.jimmer.sql.kt.KSqlClient

class SurveyAdapter(
    kSqlClient: KSqlClient
) : SurveyCommandPort {
    override suspend fun saveSurvey(survey: Survey) {


    }
}
