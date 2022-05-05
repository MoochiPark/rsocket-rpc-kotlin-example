package io.wisoft.payment

import io.ktor.http.websocket.websocketServerAccept
import io.rsocket.RSocket
import org.springframework.context.annotation.Bean
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Component
class Router {

    @Bean
    fun applicationRoutes(): RouterFunction<ServerResponse> =
        coRouter {
        }

}
