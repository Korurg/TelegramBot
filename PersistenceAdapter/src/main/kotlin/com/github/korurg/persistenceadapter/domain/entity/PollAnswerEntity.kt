package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "poll_answer")
interface PollAnswerEntity {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    @Column(name = "id")
    val id: UUID

    @Column(name = "answer")
    val answer: String

    @Column(name = "type")
    val type: String

    @Column(name = "game_id_answer")
    val gameIdAnswer: Long? //reference to GameEntity

    @Column(name = "time_answer")
    val timeAnswer: OffsetDateTime?

    @ManyToOne
    @JoinColumn(name = "poll_id")
    val poll: PollEntity
}
