package com.example.brewery.domain.service.processes.ferment

import com.example.brewery.adapter.storage.CounterStorageImpl
import com.example.brewery.domain.model.BeerStyle
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
class LagerFermentingProcess(
    private val counterStorage: CounterStorageImpl
) : BrewProcess {

    override fun supports(command: BrewProcessCommand): Boolean =
        command.processType == BrewProcessType.FERMENT && command.yeastKind == YeastKind.LAGER

    override fun run(command: BrewProcessCommand): Flow<BrewProcessResult> = flow {
        require(command.temperature in 7.0..13.0) {
            "Lager yeast requires a temperature between 7 and 13 °C"
        }
        require(command.yeastKind == YeastKind.LAGER) {
            "Lager brewing requires LAGER yeast"
        }

        delay(1000)

        val result = BrewProcessResult(
            batchId = command.batchId,
            stage = BrewProcessStage.COMPLETED,
            output = BeerStyle.Lager,
            message = "Lager fermentation completed",
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
