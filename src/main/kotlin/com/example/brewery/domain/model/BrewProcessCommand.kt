package com.example.brewery.domain.model

data class BrewProcessCommand(
    val batchId: String,
    val processType: BrewProcessType,
    val yeastKind: YeastKind,
    val temperature: Double,
)
