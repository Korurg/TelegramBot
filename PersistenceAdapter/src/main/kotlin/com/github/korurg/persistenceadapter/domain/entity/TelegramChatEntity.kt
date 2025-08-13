package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "telegram_chat")
interface TelegramChatEntity : AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Column(name = "telegram_id")
    val telegramId: Long

    @Column(name = "title")
    val title: String?

    @Column(name = "type")
    val type: String
}
