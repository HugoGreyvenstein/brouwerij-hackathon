package com.example.brewery.adapter.websocket.mapper

import com.example.brewery.adapter.websocket.dto.BrewProcessRequest
import com.example.brewery.domain.model.BoilAddition
import com.example.brewery.domain.model.BrewProcessCommand
import com.example.brewery.domain.model.BrewProcessType
import com.example.brewery.domain.model.MaltKind
import com.example.brewery.domain.model.YeastKind
import org.springframework.stereotype.Component

@Component
class BrewProcessCommandMapper {

    fun toCommand(request: BrewProcessRequest): BrewProcessCommand =
        BrewProcessCommand(
            batchId = request.batchId,
            processType = BrewProcessType.valueOf(request.processType.uppercase()),
            yeastKind = request.yeastKind?.let { YeastKind.valueOf(it.uppercase()) },
            boilAddition = request.boilAddition?.let { BoilAddition.valueOf(it.uppercase()) },
            maltKind = request.maltType?.let { MaltKind.valueOf(it.uppercase()) },
            temperature = request.temperature,
        )
}
