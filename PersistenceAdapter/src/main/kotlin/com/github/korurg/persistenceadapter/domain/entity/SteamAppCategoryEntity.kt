package com.github.korurg.persistenceadapter.domain.entity


import org.babyfish.jimmer.sql.*
import java.time.OffsetDateTime

@Entity
@Table(name = "steam_app_category")
interface SteamAppCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Key
    @Column(name = "steam_id")
    val steamId: Long

    @Column(name = "description")
    val description: String

    @Column(name = "updated")
    val updated: OffsetDateTime

    @Column(name = "created")
    val created: OffsetDateTime
}
