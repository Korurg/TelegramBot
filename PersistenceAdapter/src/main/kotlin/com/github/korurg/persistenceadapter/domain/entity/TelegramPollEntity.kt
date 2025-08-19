package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator
import java.util.*

@Entity
@Table(name = "telegram_poll")
interface TelegramPollEntity : AuditableEntity {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator::class)
    @Column(name = "id")
    val id: UUID

    @Key
    @Column(name = "telegram_id")
    val telegramId: String

    @ManyToOne
    @JoinColumn(name = "telegram_chat_id")
    val chat: TelegramChatEntity

    @Column(name = "question")
    val question: String

    @Column(name = "is_anonymous")
    val isAnonymous: Boolean

    @Column(name = "type")
    val type: String

    @OneToMany(mappedBy = "poll")
    val options: List<TelegramPollOptionEntity>
}
