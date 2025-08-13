package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "survey")
interface SurveyEntity : AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Column(name = "state")
    val state: String

    @Column(name = "chat_id")
    val chatId: Long

    @OneToMany(mappedBy = "survey")
    val polls: List<PollEntity>

    @OneToOne(mappedBy = "survey")
    val schedule: SurveyScheduleEntity?
}
