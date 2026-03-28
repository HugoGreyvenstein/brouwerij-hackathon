package com.example.brewery.domain.service.processes.boil

import com.example.brewery.adapter.storage.CounterStorageImpl
import com.example.brewery.domain.model.BeerStyle
import com.example.brewery.domain.model.BoilAddition
import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessResult
import com.example.brewery.domain.model.BrewProcessStage
import com.example.brewery.domain.model.BrewProcessType
import com.example.brewery.domain.model.YeastKind
import com.example.brewery.domain.port.BrewProcess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Component

@Component
class SpiceBoilingProcess(
    private val counterStorage: CounterStorageImpl
) : BrewProcess {

    override fun supports(command: BrewProcessCommand): Boolean =
        command.processType == BrewProcessType.BOIL && command.boilAddition == BoilAddition.SPICES

    override fun run(command: BrewProcessCommand): Flow<BrewProcessResult> = flow {
        emit(
            BrewProcessResult(
                batchId = command.batchId,
                stage = BrewProcessStage.RECEIVED,
                message = "Input received",
            )
        )

        require(command.temperature in 95.0..105.0) {
            "Temperature must be between 95 and 105 °C"
        }

        emit(
            BrewProcessResult(
                batchId = command.batchId,
                stage = BrewProcessStage.VALIDATED,
                message = "Input validated",
            )
        )

        delay(2000)

        emit(
            BrewProcessResult(
                batchId = command.batchId,
                stage = BrewProcessStage.BOILING,
                message = "Spices boiling started",
            )
        )

        delay(1000)

        val result = BrewProcessResult(
            batchId = command.batchId,
            stage = BrewProcessStage.COMPLETED,
            output = BeerStyle.Spicy,
            message = "Spices boiling completed",
        )
        counterStorage.save(result)
        println("Result with updated values: $result")
        emit(result)
    }.catch { exception ->
        emit(
            BrewProcessResult(
                batchId = command.batchId,
                stage = BrewProcessStage.FAILED,
                message = exception.message ?: "Brewing process failed",
            )
        )
    }
}
