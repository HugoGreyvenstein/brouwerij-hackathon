package com.example.brewery.domain.service.processes.mash

import com.example.brewery.adapter.storage.CounterStorageImpl
import com.example.brewery.domain.model.BeerStyle
import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessResult
import com.example.brewery.domain.model.BrewProcessStage
import com.example.brewery.domain.model.BrewProcessType
import com.example.brewery.domain.model.MaltKind
import com.example.brewery.domain.model.YeastKind
import com.example.brewery.domain.port.BrewProcess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Component

@Component
class LightMashingProcess(
    private val counterStorage: CounterStorageImpl
) : BrewProcess {

    override fun supports(command: BrewProcessCommand): Boolean =
        command.processType == BrewProcessType.MASH && command.maltKind == MaltKind.LIGHT

    override fun run(command: BrewProcessCommand): Flow<BrewProcessResult> = flow {
        emit(
            BrewProcessResult(
                batchId = command.batchId,
                stage = BrewProcessStage.RECEIVED,
                message = "Input received",
            )
        )

        require(command.temperature in 65.0..70.0) {
            "Temperature must be between 65 and 70 °C"
        }
        require(command.maltKind == MaltKind.LIGHT) {
            "Light brewing requires Light malt type"
        }

        emit(
            BrewProcessResult(
                batchId = command.batchId,
                stage = BrewProcessStage.VALIDATED,
                message = "Input validated",
            )
        )

        delay(500)

        emit(
            BrewProcessResult(
                batchId = command.batchId,
                stage = BrewProcessStage.FERMENTING,
                message = "Light malt mashing started",
            )
        )

        delay(1000)

        val result = BrewProcessResult(
            batchId = command.batchId,
            stage = BrewProcessStage.COMPLETED,
            output = BeerStyle.Light,
            message = "Light malt mashing completed",
        )
        counterStorage.save(result)
        println("Result with updated values: $result")
        emit(result)
    }.catch { exception ->
        val result = BrewProcessResult(
            batchId = command.batchId,
            stage = BrewProcessStage.FAILED,
            message = exception.message ?: "Brewing process failed",
        )
        counterStorage.setAllValues(result)
        println("Result with updated values: $result")
        emit(result)
    }
}
