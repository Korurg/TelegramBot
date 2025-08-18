package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "poll")
interface PollEntity : AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Key
    @Column(name = "telegram_id")
    val telegramId: String

    @Column(name = "type")
    val type: String

    @OneToMany(mappedBy = "poll")
    val answers: List<PollAnswerEntity>
}
