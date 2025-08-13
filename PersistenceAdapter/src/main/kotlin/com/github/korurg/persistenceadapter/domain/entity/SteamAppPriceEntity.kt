package com.github.korurg.persistenceadapter.domain.entity


import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "steam_app_price")
interface SteamAppPriceEntity : AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long

    @Column(name = "currency")
    val currency: String

    @Column(name = "initial_formatted")
    val initialFormatted: String

    @Column(name = "final_formatted")
    val finalFormatted: String

    @Column(name = "initial")
    val initial: Long

    @Column(name = "final")
    val final: Long

    @Column(name = "discount_percent")
    val discountPercent: Long

    @Key
    @OneToOne
    @JoinColumn(name = "steam_app_id")
    val steamApp: SteamAppEntity
}
