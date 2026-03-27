package com.example.brewery.domain.service

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
class AleBrewingProcess : BrewProcess {

    override fun supports(type: BrewProcessType): Boolean =
        type == BrewProcessType.ALE

    override fun run(command: BrewProcessCommand): Flow<BrewProcessResult> = flow {
        emit(
            BrewProcessResult(
                batchId = command.batchId,
                stage = BrewProcessStage.RECEIVED,
                message = "Input received",
            )
        )

        require(command.temperature in 0.0..40.0) {
            "Temperature must be between 0 and 40 °C"
        }
        require(command.yeastKind == YeastKind.ALE) {
            "Ale brewing requires ALE yeast"
        }
        require(command.temperature in 15.0..24.0) {
            "Ale yeast requires a temperature between 15 and 24 °C"
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
                message = "Ale fermentation started",
            )
        )

        delay(1000)

        emit(
            BrewProcessResult(
                batchId = command.batchId,
                stage = BrewProcessStage.COMPLETED,
                output = BeerStyle.Ale,
                message = "Ale fermentation completed",
            )
        )
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
