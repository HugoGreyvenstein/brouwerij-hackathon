package com.example.brewery.domain.service

import com.example.brewery.domain.model.BrewBatch
import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessResult
import com.example.brewery.domain.port.BrewProcess
import com.example.brewery.domain.port.ForRunningBrewProcess
import com.example.brewery.domain.port.ForStoringBrewBatches
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service

@Service
class RunBrewProcess(
    private val brewBatchRepository: ForStoringBrewBatches,
    private val brewProcesses: List<BrewProcess>,
) : ForRunningBrewProcess {

    override fun invoke(command: BrewProcessCommand): Flow<BrewProcessResult> {
        val brewProcess = brewProcesses.firstOrNull { it.supports(command.processType) }
            ?: throw IllegalArgumentException("Unsupported brew process type: ${command.processType}")

        return brewProcess.run(command)
            .onEach { result ->
                brewBatchRepository.save(
                    BrewBatch(
                        batchId = command.batchId,
                        processType = command.processType,
                        yeastKind = command.yeastKind,
                        temperature = command.temperature,
                        stage = result.stage,
                        output = result.output,
                    )
                )
            }
    }
}
