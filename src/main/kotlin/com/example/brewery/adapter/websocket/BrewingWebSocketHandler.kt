package com.example.brewery.adapter.websocket

import com.example.brewery.adapter.websocket.dto.BrewProcessRequest
import com.example.brewery.adapter.websocket.mapper.BrewProcessCommandMapper
import com.example.brewery.adapter.websocket.mapper.BrewProcessResponseMapper
import com.example.brewery.domain.model.BrewProcessResult
import com.example.brewery.domain.model.BrewProcessStage
import com.example.brewery.domain.port.ForRunningBrewProcess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
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
import tools.jackson.databind.ObjectMapper

@Component
class BrewingWebSocketHandler(
    private val objectMapper: ObjectMapper,
    private val brewProcessCommandMapper: BrewProcessCommandMapper,
    private val brewProcessResponseMapper: BrewProcessResponseMapper,
    private val runBrewProcess: ForRunningBrewProcess,
) : WebSocketHandler {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(session: WebSocketSession): Mono<Void> =
        session.receive()
            .asFlow()
            .flatMapConcat { message ->
                // Use a flow-based approach to handle every message
                flow {
                    val payload = message.payloadAsText
                    // DataBuffer is automatically released by Spring after
                    // the receive() signal is processed unless retained.
                    // No need for retain() here if just reading text.

                    val request = toRequest(payload)
                    val command = brewProcessCommandMapper.toCommand(request)

                    // Emit the results from the domain service
                    emitAll(runBrewProcess(command))
                }.catch { exception ->
                    emit(BrewProcessResult(stage = BrewProcessStage.FAILED, batchId = "", message = "Error: ${exception.message}"))
                }
            }
            .map { result ->
                val json = objectMapper.writeValueAsString(brewProcessResponseMapper.toResponse(result))
                session.textMessage(json)
            }
            .asPublisher()
            .let { session.send(it) }

    private fun toRequest(message: String): BrewProcessRequest =
        objectMapper.readValue(message, BrewProcessRequest::class.java)
}
