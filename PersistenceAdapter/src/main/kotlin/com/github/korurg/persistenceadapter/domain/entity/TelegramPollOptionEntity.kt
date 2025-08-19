package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.UUID

@Entity
@Table(name = "telegram_poll_option")
interface TelegramPollOptionEntity: AuditableEntity {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    @Column(name = "id")
    val id: UUID

    @Column(name = "telegram_id")
    val telegramId: Int

    @Column(name = "option")
    val option: String

    @ManyToOne
    @JoinColumn(name = "telegram_poll_id")
    val poll: TelegramPollEntity

    @OneToMany(mappedBy = "pollOption")
    val answers: List<TelegramPollAnswerEntity>
}
