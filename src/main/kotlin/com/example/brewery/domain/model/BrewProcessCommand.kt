package com.example.brewery.domain.model

data class BrewProcessCommand(
    val batchId: String,
    val processType: BrewProcessType,
    val yeastKind: YeastKind?,
    val boilAddition: BoilAddition?,
    val maltKind: MaltKind?,
    val temperature: Double,
)
