package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
@Table(name = "coop_poll")
interface CoopPollEntity : AuditableEntity {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    @Column(name = "id")
    val id: UUID

    @Key
    @OneToOne
    @JoinColumn(name = "telegram_poll_id", referencedColumnName = "id")
    val poll: TelegramPollEntity

    @Column(name = "type")
    val type: String

    @OneToMany(mappedBy = "poll")
    val options: List<CoopPollOptionEntity>
}
