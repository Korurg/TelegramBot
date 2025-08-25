package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.*

@Entity
@Table(name = "survey")
interface SurveyEntity : AuditableEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    @Column(name = "id")
    val id: UUID

    @Column(name = "state")
    val state: String

    @Column(name = "chat_id")
    val chatId: Long

    @OneToOne
    @JoinColumn(name = "game_poll_id")
    val gamePoll: CoopPollEntity?

    @OneToOne
    @JoinColumn(name = "time_poll_id")
    val timePoll: CoopPollEntity

    @ManyToOne
    @JoinTable(
        name = "survey_survey_schedule",
        joinColumnName = "survey_id",
        inverseJoinColumnName = "survey_schedule_id"
    )
    val schedule: SurveyScheduleEntity?
}
