package com.github.korurg.persistenceadapter.config.interceptor

import com.github.korurg.persistenceadapter.domain.entity.AuditableEntity
import com.github.korurg.persistenceadapter.domain.entity.AuditableEntityDraft
import org.babyfish.jimmer.sql.DraftInterceptor
import java.time.OffsetDateTime

class AuditableInterceptor : DraftInterceptor<AuditableEntity, AuditableEntityDraft> {

    override fun beforeSave(draft: AuditableEntityDraft, original: AuditableEntity?) {
        val now = OffsetDateTime.now()

        if (original == null) {
            draft.created = now
        }
        draft.updated = now
    }
}
