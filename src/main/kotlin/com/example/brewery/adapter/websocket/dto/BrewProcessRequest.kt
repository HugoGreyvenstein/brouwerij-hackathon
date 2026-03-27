package com.example.brewery.adapter.websocket.dto

data class BrewProcessRequest(
    val batchId: String,
    val processType: String,
    val yeastKind: String?,
    val boilAddition: String?,
    val maltType: String?,
    val temperature: Double,
)
