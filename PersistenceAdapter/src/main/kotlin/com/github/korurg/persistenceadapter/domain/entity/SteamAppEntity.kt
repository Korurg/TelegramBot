package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.*
import java.time.OffsetDateTime

@Entity
@Table(name = "steam_app")
interface SteamAppEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Column(name = "name")
    val name: String

    @Column(name = "type")
    val type: String?

    @Key
    @Column(name = "steam_id")
    val steamId: Long

    @Column(name = "version")
    val version: Int?

    @Column(name = "description")
    val description: String?

    @Column(name = "short_description")
    val shortDescription: String?

    @Column(name = "fill_state")
    val fillState: String

    @ManyToMany
    @JoinTable(
        name = "steam_app_steam_app_category",
        joinColumns = [JoinColumn(name = "steam_app_id")],
        inverseJoinColumns = [JoinColumn(name = "steam_app_category_id")]
    )
    val categories: List<SteamAppCategoryEntity>

    @OneToOne(mappedBy = "steamApp")
    val game: GameEntity?

    @OneToOne(mappedBy = "steamApp")
    val currentPrice: SteamAppPriceEntity?

    @Column(name = "updated")
    val updated: OffsetDateTime

    @Column(name = "created")
    val created: OffsetDateTime

}
