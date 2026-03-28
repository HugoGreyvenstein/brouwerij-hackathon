package com.example.brewery.adapter.storage

import com.example.brewery.domain.model.BeerStyle
import com.example.brewery.domain.model.BrewBatch
import com.example.brewery.domain.model.BrewProcessResult
import com.example.brewery.domain.port.CountStorage
import org.springframework.stereotype.Repository

@Repository
class CounterStorageImpl : CountStorage {
    // Light
    private var mashed1: Int = 0
    // Dark
    private var mashed2: Int = 0

    // Light Hoppy
    private var boiled1: Int = 0
    // Light Spicy
    private var boiled2: Int = 0
    // Dark Hoppy
    private var boiled3: Int = 0
    // Dark Spicy
    private var boiled4: Int = 0

    // Light Hoppy Ale
    private var fermented1: Int = 0
    // Light Hoppy Lager
    private var fermented2: Int = 0
    // Light Spicy Ale
    private var fermented3: Int = 0
    // Light Spicy Lager
    private var fermented4: Int = 0

    // Dark Hoppy Ale
    private var fermented5: Int = 0
    // Dark Hoppy Lager
    private var fermented6: Int = 0
    // Dark Spicy Ale
    private var fermented7: Int = 0
    // Dark Spicy Lager
    private var fermented8: Int = 0

    override fun save(brewProcessResult: BrewProcessResult) {
        val beerStyle = brewProcessResult.output
        if (beerStyle == BeerStyle.Light) {
            mashed1 += 1
            setAllValues(brewProcessResult)
        } else if (beerStyle == BeerStyle.Dark) {
            mashed2 += 1
            setAllValues(brewProcessResult)
        } else if (beerStyle == BeerStyle.Hoppy || beerStyle == BeerStyle.Spicy) {
            if (mashed1 < 1 && mashed2 < 1) {
                throw Exception("Not enough mashes to perform a boil, please mash more")
            }
            if (mashed1 > 0) {
                mashed1 -= 1
                if (beerStyle == BeerStyle.Hoppy) {
                    boiled1 += 1
                } else {
                    boiled2 += 1
                }
                setAllValues(brewProcessResult)
            } else {
                mashed2 -= 1
                if (beerStyle == BeerStyle.Hoppy) {
                    boiled3 += 1
                } else {
                    boiled4 += 1
                }
                setAllValues(brewProcessResult)
            }
        } else if (beerStyle == BeerStyle.Ale || beerStyle == BeerStyle.Lager) {
            if (boiled1 > 0) {
                boiled1 -= 1
                if (beerStyle == BeerStyle.Ale) {
                    fermented1 += 1
                } else {
                    fermented2 += 1
                }
                setAllValues(brewProcessResult)
                return
            }
            if (boiled2 > 0) {
                boiled2 -= 1
                if (beerStyle == BeerStyle.Ale) {
                    fermented3 += 1
                } else {
                    fermented4 += 1
                }
                setAllValues(brewProcessResult)
                return
            }
            if (boiled3 > 0) {
                boiled3 -= 1
                if (beerStyle == BeerStyle.Ale) {
                    fermented5 += 1
                } else {
                    fermented6 += 1
                }
                setAllValues(brewProcessResult)
                return
            }
            if (boiled4 > 0) {
                boiled4 -= 1
                if (beerStyle == BeerStyle.Ale) {
                    fermented7 += 1
                } else {
                    fermented8 += 1
                }
                setAllValues(brewProcessResult)
                return
            }
            throw Exception("Not enough boils to ferment, please boil more mashes")
        }
    }


    override fun setAllValues(result: BrewProcessResult) {
        result.mashed1 = mashed1
        result.mashed2 = mashed2

        result.boiled1 = boiled1
        result.boiled2 = boiled2
        result.boiled3 = boiled3
        result.boiled4 = boiled4

        result.fermented1 = fermented1
        result.fermented2 = fermented2
        result.fermented3 = fermented3
        result.fermented4 = fermented4

        result.fermented5 = fermented5
        result.fermented6 = fermented6
        result.fermented7 = fermented7
        result.fermented8 = fermented8
    }
}