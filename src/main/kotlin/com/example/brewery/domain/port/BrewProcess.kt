package com.example.brewery.domain.port

import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessResult
import com.example.brewery.domain.model.BrewProcessType
import kotlinx.coroutines.flow.Flow

interface BrewProcess {
    fun supports(type: BrewProcessType): Boolean
    fun run(command: BrewProcessCommand): Flow<BrewProcessResult>
}
