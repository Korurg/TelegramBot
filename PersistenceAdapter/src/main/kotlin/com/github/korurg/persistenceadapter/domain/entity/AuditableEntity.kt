package com.github.korurg.persistenceadapter.domain.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.OffsetDateTime

@MappedSuperclass
interface AuditableEntity {

    @Column(name = "created")
    val created: OffsetDateTime

    @Column(name = "updated")
    val updated: OffsetDateTime
}
