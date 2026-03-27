package com.example.brewery.adapter.websocket

import com.example.brewery.adapter.websocket.dto.BrewProcessRequest
import com.example.brewery.adapter.websocket.mapper.BrewProcessCommandMapper
import com.example.brewery.adapter.websocket.mapper.BrewProcessResponseMapper
import com.example.brewery.domain.model.BrewProcessResult
import com.example.brewery.domain.model.BrewProcessStage
import com.example.brewery.domain.port.ForRunningBrewProcess
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.asPublisher
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class BrewingWebSocketHandler(
    private val objectMapper: ObjectMapper,
    private val brewProcessCommandMapper: BrewProcessCommandMapper,
    private val brewProcessResponseMapper: BrewProcessResponseMapper,
    private val runBrewProcess: ForRunningBrewProcess,
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> =
        mono {
            session.receive()
                .asFlow()
                .map(::toRequest)
                .first()
        }.flatMap { request ->
            val resultFlow = try {
                val command = brewProcessCommandMapper.toCommand(request)
                runBrewProcess(command)
            } catch (exception: Exception) {
                flowOf(
                    BrewProcessResult(
                        batchId = request.batchId,
                        stage = BrewProcessStage.FAILED,
                        message = exception.message ?: "Invalid request",
                    )
                )
            }

            session.send(
                resultFlow
                    .map(brewProcessResponseMapper::toResponse)
                    .map(objectMapper::writeValueAsString)
                    .map(session::textMessage)
                    .asPublisher()
            )
        }

    private fun toRequest(message: WebSocketMessage): BrewProcessRequest =
        objectMapper.readValue(message.payloadAsText, BrewProcessRequest::class.java)
}
