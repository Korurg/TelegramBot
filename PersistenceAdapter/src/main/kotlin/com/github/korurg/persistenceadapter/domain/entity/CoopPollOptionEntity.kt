package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "coop_poll_option")
interface CoopPollOptionEntity {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    @Column(name = "id")
    val id: UUID

    @Column(name = "type")
    val type: String

    @Column(name = "game_id_option")
    val gameIdOption: Long? //reference to GameEntity

    @Column(name = "time_option")
    val timeOption: OffsetDateTime?

    @Key
    @ManyToOne
    @JoinColumn(name = "coop_poll_id", referencedColumnName = "id")
    val poll: CoopPollEntity

    @OneToOne
    @JoinColumn(name = "telegram_poll_option_id", referencedColumnName = "id")
    val option: TelegramPollOptionEntity
}
