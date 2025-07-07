package com.github.korurg.persistence.repository

import org.jooq.DSLContext
import org.jooq.TableField
import org.jooq.UpdatableRecord
import org.jooq.impl.DSL
import org.jooq.impl.TableImpl

abstract class AbstractJooqRepository<RECORD : UpdatableRecord<RECORD>, ID>(
    private val dslContext: DSLContext,
    private val table: TableImpl<RECORD>,
    private val idField: TableField<RECORD, ID>
) : JooqRepository<RECORD, ID> {

    override fun save(record: RECORD): RECORD {
        return if (record[idField] == null) {
            dslContext.insertInto(table)
                .set(record)
                .returning()
                .fetchOne()!!
        } else {
            dslContext.update(table)
                .set(record)
                .where(idField.eq(record[idField]))
                .execute()

            record
        }
    }

    override fun findById(id: ID): RECORD? {
        return dslContext.selectFrom(table)
            .where(idField.eq(id))
            .fetchOne()
    }

    override fun delete(id: ID): Boolean {
        return dslContext.deleteFrom(table)
            .where(idField.eq(id))
            .execute() != 0
    }

    override fun saveAll(records: Collection<RECORD>): Collection<RECORD> {
        if (records.isEmpty()) {
            return emptyList()
        }
        val savedRecords: MutableList<RECORD> = ArrayList()
        dslContext.transaction { config ->
            val transactionalDsl = DSL.using(config)

            val (newRecords, existingRecords) = records.partition { it[idField] == null }

            val insertedRecords = if (newRecords.isNotEmpty()) {
                newRecords.map {
                    transactionalDsl.insertInto(table)
                        .set(it)
                        .returning()
                        .fetchOne()!!
                }
            } else {
                emptyList()
            }

            if (existingRecords.isNotEmpty()) {
                transactionalDsl.batchUpdate(existingRecords).execute()
            }

            savedRecords.addAll(insertedRecords)
            savedRecords.addAll(existingRecords)
        }

        return savedRecords
    }
}
