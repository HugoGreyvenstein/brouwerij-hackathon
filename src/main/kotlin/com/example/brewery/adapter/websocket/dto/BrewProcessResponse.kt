package com.example.brewery.adapter.websocket.dto

data class BrewProcessResponse(
    val batchId: String,
    val stage: String,
    val output: String? = null,
    val message: String,
)
