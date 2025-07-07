package com.github.korurg.persistence.repository

interface JooqRepository<RECORD, ID> {

    fun save(record: RECORD): RECORD

    fun saveAll(records: Collection<RECORD>) : Collection<RECORD>

    fun findById(id: ID): RECORD?

    fun delete(id: ID): Boolean
}
