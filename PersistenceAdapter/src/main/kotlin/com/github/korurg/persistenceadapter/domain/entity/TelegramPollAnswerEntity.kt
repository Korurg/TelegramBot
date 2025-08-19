package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.*

@Entity
@Table(name = "telegram_poll_answer")
interface TelegramPollAnswerEntity: AuditableEntity {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    @Column(name = "id")
    val id: UUID

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: TelegramUserEntity

    @ManyToOne
    @JoinColumn(name = "telegram_poll_option_id")
    val pollOption: TelegramPollOptionEntity
}
