package com.example.brewery.adapter.websocket.dto

data class BrewProcessResponse(
    val batchId: String,
    val stage: String,
    val output: String? = null,
    val mashed1: Int = 0,
    val mashed2: Int = 0,
    val boiled1: Int = 0,
    val boiled2: Int = 0,
    val boiled3: Int = 0,
    val boiled4: Int = 0,
    val fermented1: Int = 0,
    val fermented2: Int = 0,
    val fermented3: Int = 0,
    val fermented4: Int = 0,
    val fermented5: Int = 0,
    val fermented6: Int = 0,
    val fermented7: Int = 0,
    val fermented8: Int = 0,
    val message: String,
)
