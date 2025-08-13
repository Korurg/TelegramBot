package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "poll")
interface PollEntity : AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Column(name = "telegram_id")
    val telegramId: Long

    @Column(name = "type")
    val type: String

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: SurveyEntity

    @OneToMany(mappedBy = "poll")
    val answers: List<PollAnswerEntity>
}
