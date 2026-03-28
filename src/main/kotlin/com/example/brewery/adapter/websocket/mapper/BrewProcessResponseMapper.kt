package com.example.brewery.adapter.websocket.mapper

import com.example.brewery.adapter.websocket.dto.BrewProcessResponse
import com.example.brewery.domain.model.BrewProcessResult
import org.springframework.stereotype.Component

@Component
class BrewProcessResponseMapper {

    fun toResponse(result: BrewProcessResult): BrewProcessResponse =
        BrewProcessResponse(
            batchId = result.batchId,
            stage = result.stage.name,
            output = result.output?.name,
            mashed1 = result.mashed1,
            mashed2 = result.mashed2,
            boiled1 = result.boiled1,
            boiled2 = result.boiled2,
            boiled3 = result.boiled3,
            boiled4 = result.boiled4,
            fermented1 = result.fermented1,
            fermented2 = result.fermented2,
            fermented3 = result.fermented3,
            fermented4 = result.fermented4,
            fermented5 = result.fermented5,
            fermented6 = result.fermented6,
            fermented7 = result.fermented7,
            fermented8 = result.fermented8,
            message = result.message,
        )
}
