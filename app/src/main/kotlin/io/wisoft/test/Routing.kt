package io.wisoft.test

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting(
    shoppingService: ShoppingService = ShoppingService(),
) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/payment") {
            val request = call.receive<Request.Payment>()
            println(request)
            shoppingService.payment(request)
            call.respondText("Test")
        }
    }
}
