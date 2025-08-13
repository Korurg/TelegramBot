package com.github.korurg.persistenceadapter.domain.entity


import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "steam_app_category")
interface SteamAppCategoryEntity : AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Key
    @Column(name = "steam_id")
    val steamId: Long

    @Column(name = "description")
    val description: String
}
