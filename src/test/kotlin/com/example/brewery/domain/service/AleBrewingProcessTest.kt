package com.example.brewery.domain.service

import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessStage
import com.example.brewery.domain.model.BrewProcessType
import com.example.brewery.domain.model.YeastKind
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AleBrewingProcessTest {

    private val underTest = AleBrewingProcess()

    @Test
    fun `should emit completed result for valid ale input`() = runTest {
        val command = BrewProcessCommand(
            batchId = "batch-1",
            processType = BrewProcessType.ALE,
            yeastKind = YeastKind.ALE,
            temperature = 20.0,
        )

        val results = underTest.run(command).toList()

        assertEquals(
            listOf(
                BrewProcessStage.RECEIVED,
                BrewProcessStage.VALIDATED,
                BrewProcessStage.FERMENTING,
                BrewProcessStage.COMPLETED,
            ),
            results.map { it.stage },
        )
        assertEquals("Ale fermentation completed", results.last().message)
    }

    @Test
    fun `should emit failed result for invalid ale temperature`() = runTest {
        val command = BrewProcessCommand(
            batchId = "batch-2",
            processType = BrewProcessType.ALE,
            yeastKind = YeastKind.ALE,
            temperature = 10.0,
        )

        val results = underTest.run(command).toList()

        assertEquals(listOf(BrewProcessStage.RECEIVED, BrewProcessStage.FAILED), results.map { it.stage })
        assertEquals("Ale yeast requires a temperature between 15 and 24 °C", results.last().message)
    }
}
