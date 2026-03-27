package com.example.brewery.domain.model

data class BrewProcessResult(
    val batchId: String,
    val stage: BrewProcessStage,
    val output: BeerStyle? = null,
    val message: String,
)
