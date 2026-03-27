package com.example.brewery.adapter.websocket

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
class BrewingWebSocketConfig(
    private val brewingWebSocketHandler: BrewingWebSocketHandler,
) {

    @Bean
    fun brewingWebSocketHandlerMapping(): HandlerMapping =
        SimpleUrlHandlerMapping().apply {
            urlMap = mapOf("/ws/brewing" to brewingWebSocketHandler)
            order = 1
        }

    @Bean
    fun webSocketHandlerAdapter(): WebSocketHandlerAdapter =
        WebSocketHandlerAdapter()
}
