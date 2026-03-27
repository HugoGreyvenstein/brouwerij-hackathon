package com.example.brewery.domain.port

import com.example.brewery.domain.model.BrewBatch

interface ForStoringBrewBatches {
    fun save(batch: BrewBatch): BrewBatch
    fun findByBatchId(batchId: String): BrewBatch?
}
