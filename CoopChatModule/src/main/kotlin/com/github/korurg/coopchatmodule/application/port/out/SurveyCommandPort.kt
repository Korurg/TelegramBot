package com.github.korurg.coopchatmodule.application.port.out

import com.github.korurg.coopchatmodule.domain.Survey

interface SurveyCommandPort {
    suspend fun saveSurvey(survey: Survey)
}
