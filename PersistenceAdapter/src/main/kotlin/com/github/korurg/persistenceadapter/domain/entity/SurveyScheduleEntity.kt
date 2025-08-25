package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*
import java.time.LocalDate

@Entity
@Table(name = "survey_schedule")
interface SurveyScheduleEntity : AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @OneToMany(mappedBy = "schedule")
    val surveys: List<SurveyEntity>

    @Column(name = "start_date")
    val startDate: LocalDate

    @Column(name = "end_date")
    val endDate: LocalDate?

    @Column(name = "type")
    val type: String

    @Column(name = "interval_cron")
    val intervalCron: String

    @Column(name = "skip_until")
    val skipUntil: LocalDate?

    @Column(name = "timezone")
    val timezone: String
}
