package com.example.brewery.adapter.storage

import com.example.brewery.domain.model.BoilAddition
import com.example.brewery.domain.model.BrewBatch
import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.MaltKind
import com.example.brewery.domain.model.YeastKind
import com.example.brewery.domain.port.ForStoringBrewBatches
import com.example.brewery.domain.service.processes.boil.SpiceBoilingProcess
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryBrewBatchRepository : ForStoringBrewBatches {

    private val batches = ConcurrentHashMap<String, BrewBatch>()

    private val boilingCounters = ConcurrentHashMap<BoilAddition, Int>()
    private val mashingCounters = ConcurrentHashMap<MaltKind, Int>()
    private val fermentingCounters = ConcurrentHashMap<YeastKind, Int>()

    override fun save(batch: BrewBatch): BrewBatch {
        batches[batch.batchId] = batch
        println("Batches $batches")
        return batch
    }

    override fun incrementCounters(command: BrewProcessCommand) {
        if (command.maltKind != null) {
            val mashingCounter = mashingCounters.getOrDefault(command.maltKind, 0) + 1
            println("mashingCounter $mashingCounter")
        }

        if (command.boilAddition != null) {
            val boilingCounter = boilingCounters.getOrDefault(command.boilAddition, 0) + 1
            println("BoilingCounter $boilingCounter")
        }

        if (command.yeastKind != null) {
            val fermentingCounter = fermentingCounters.getOrDefault(command.yeastKind, 0) + 1
            println("fermentingCounter $fermentingCounter")
        }
    }

    override fun findByBatchId(batchId: String): BrewBatch? =
        batches[batchId]
}
