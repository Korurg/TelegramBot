package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table
import java.sql.Date
import java.time.LocalDate

@Entity
@Table(name = "survey_schedule")
interface SurveyScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @OneToOne
    @JoinColumn(name = "survey_id")
    val survey: SurveyEntity

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
