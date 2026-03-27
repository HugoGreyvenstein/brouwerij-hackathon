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
            message = result.message,
        )
}
