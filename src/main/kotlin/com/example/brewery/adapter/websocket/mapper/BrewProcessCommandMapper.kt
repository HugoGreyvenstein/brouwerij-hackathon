package com.example.brewery.adapter.websocket.mapper

import com.example.brewery.adapter.websocket.dto.BrewProcessRequest
import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessType
import com.example.brewery.domain.model.YeastKind
import org.springframework.stereotype.Component

@Component
class BrewProcessCommandMapper {

    fun toCommand(request: BrewProcessRequest): BrewProcessCommand =
        BrewProcessCommand(
            batchId = request.batchId,
            processType = BrewProcessType.valueOf(request.processType.uppercase()),
            yeastKind = YeastKind.valueOf(request.yeastKind.uppercase()),
            temperature = request.temperature,
        )
}
