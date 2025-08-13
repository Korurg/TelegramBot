package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "poll_answer")
interface PollAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Column(name = "answer")
    val answer: String

    @Column(name = "type")
    val type: String

    @Column(name = "data")
    val data: String

    @ManyToOne
    @JoinColumn(name = "poll_id")
    val poll: PollEntity
}
