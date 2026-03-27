package com.example.brewery.domain.service

import com.example.brewery.adapter.storage.InMemoryBrewBatchRepository
import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessType
import com.example.brewery.domain.model.YeastKind
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class RunBrewProcessTest {

    private val repository = InMemoryBrewBatchRepository()
    private val underTest = RunBrewProcess(
        brewBatchRepository = repository,
        brewProcesses = listOf(AleBrewingProcess(), LagerBrewingProcess()),
    )

    @Test
    fun `should store final batch state`() = runTest {
        val command = BrewProcessCommand(
            batchId = "batch-1",
            processType = BrewProcessType.LAGER,
            yeastKind = YeastKind.LAGER,
            temperature = 10.0,
        )

        val results = underTest(command).toList()
        val savedBatch = repository.findByBatchId("batch-1")

        assertNotNull(savedBatch)
        assertEquals(results.last().stage, savedBatch?.stage)
        assertEquals(results.last().output, savedBatch?.output)
    }
}
