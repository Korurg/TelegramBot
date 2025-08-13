package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "telegram_user")
interface TelegramUserEntity : AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Column(name = "telegram_id")
    val telegramId: Long

    @Column(name = "first_name")
    val firstName: String

    @Column(name = "last_name")
    val lastName: String?

    @Column(name = "username")
    val username: String?

    @Column(name = "is_bot")
    val isBot: Boolean

    @Column(name = "language_code")
    val languageCode: String?
}
