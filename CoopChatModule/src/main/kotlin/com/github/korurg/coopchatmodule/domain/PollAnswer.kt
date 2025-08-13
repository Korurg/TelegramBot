package com.github.korurg.coopchatmodule.domain

import com.github.korurg.coopchatmodule.domain.enums.AnswerType
import com.github.korurg.coopchatmodule.domain.id.PollAnswerId

data class PollAnswer(
    val id: PollAnswerId?,
    val answer: String,
    val type: AnswerType,
    val data: String?
)
