package com.example.brewery.adapter.websocket.dto

data class BrewProcessResponse(
    val batchId: String,
    val stage: String,
    val output: String? = null,
//    val mashed1: Int,
//    val mashed2: Int,
//    val boiled1: Int,
//    val boiled2: Int,
//    val boiled3: Int,
//    val boiled4: Int,
//    val fermented1: Int,
//    val fermented2: Int,
//    val fermented3: Int,
//    val fermented4: Int,
//    val fermented5: Int,
//    val fermented6: Int,
//    val fermented7: Int,
//    val fermented8: Int,
    val message: String,
)
