package com.example.brewery.domain.port

import com.example.brewery.domain.model.BrewBatch
import com.example.brewery.domain.model.BrewProcessCommand

interface ForStoringBrewBatches {
    fun save(batch: BrewBatch): BrewBatch
    fun incrementCounters(command: BrewProcessCommand)
    fun findByBatchId(batchId: String): BrewBatch?
}
