package com.example.brewery.adapter.storage

import com.example.brewery.domain.model.BrewBatch
import com.example.brewery.domain.port.ForStoringBrewBatches
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryBrewBatchRepository : ForStoringBrewBatches {

    private val batches = ConcurrentHashMap<String, BrewBatch>()

    override fun save(batch: BrewBatch): BrewBatch {
        batches[batch.batchId] = batch
        return batch
    }

    override fun findByBatchId(batchId: String): BrewBatch? =
        batches[batchId]
}
