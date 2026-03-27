package com.example.brewery.domain.model

data class BrewBatch(
    val batchId: String,
    val processType: BrewProcessType,
    val yeastKind: YeastKind,
    val temperature: Double,
    val stage: BrewProcessStage,
    val output: BeerStyle? = null,
)
