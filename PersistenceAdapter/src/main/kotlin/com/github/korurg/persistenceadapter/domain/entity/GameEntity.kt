package com.github.korurg.persistenceadapter.domain.entity


import org.babyfish.jimmer.sql.*
import java.time.OffsetDateTime

@Entity
@Table(name = "game")
interface GameEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @Key
    @Column(name = "name")
    val name: String

    @Column(name = "max_players")
    val maxPlayers: Int

    @Column(name = "min_players")
    val minPlayers: Int

    @Key
    @OneToOne
    @JoinColumn(name = "steam_app_id")
    val steamApp: SteamAppEntity?

    @Column(name = "updated")
    val updated: OffsetDateTime

    @Column(name = "created")
    val created: OffsetDateTime
}
