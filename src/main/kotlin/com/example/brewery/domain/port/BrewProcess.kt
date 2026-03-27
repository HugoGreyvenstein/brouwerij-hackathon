package com.example.brewery.domain.port

import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessResult
import kotlinx.coroutines.flow.Flow

interface BrewProcess {
    fun supports(command: BrewProcessCommand): Boolean
    fun run(command: BrewProcessCommand): Flow<BrewProcessResult>
}
