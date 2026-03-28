package com.example.brewery.domain.port

import com.example.brewery.domain.model.BrewBatch
import com.example.brewery.domain.model.BrewProcessResult

interface CountStorage {
    fun save(brewProcessResult: BrewProcessResult)
    fun setAllValues(result: BrewProcessResult)
}