package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "poll_option")
interface PollOptionEntity {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    @Column(name = "id")
    val id: UUID

    @Column(name = "telegram_id")
    val telegramId: Int

    @Column(name = "answer")
    val answer: String

    @Column(name = "type")
    val type: String

    @Column(name = "game_id_option")
    val gameIdAnswer: Long? //reference to GameEntity

    @Column(name = "time_option")
    val timeAnswer: OffsetDateTime?

    @ManyToOne
    @JoinColumn(name = "poll_id")
    val poll: PollEntity
}
