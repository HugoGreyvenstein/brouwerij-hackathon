package com.example.brewery.domain.port

import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessResult
import kotlinx.coroutines.flow.Flow

fun interface ForRunningBrewProcess {
    operator fun invoke(command: BrewProcessCommand): Flow<BrewProcessResult>
}
