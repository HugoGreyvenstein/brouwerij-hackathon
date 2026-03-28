package com.example.brewery.domain.model

data class BrewProcessResult(
    val batchId: String,
    val stage: BrewProcessStage,
    val output: BeerStyle? = null,
    // Light
    var mashed1: Int = 0,
    // Dark
    var mashed2: Int = 0,

    // Light Hoppy
    var boiled1: Int = 0,
    // Light Spicy
    var boiled2: Int = 0,
    // Dark Hoppy
    var boiled3: Int = 0,
    // Dark Spicy
    var boiled4: Int = 0,

    // Light Hoppy Ale
    var fermented1: Int = 0,
    // Light Hoppy Lager
    var fermented2: Int = 0,
    // Light Spicy Ale
    var fermented3: Int = 0,
    // Light Spicy Lager
    var fermented4: Int = 0,

    // Dark Hoppy Ale
    var fermented5: Int = 0,
    // Dark Hoppy Lager
    var fermented6: Int = 0,
    // Dark Spicy Ale
    var fermented7: Int = 0,
    // Dark Spicy Lager
    var fermented8: Int = 0,

    val message: String,
)
