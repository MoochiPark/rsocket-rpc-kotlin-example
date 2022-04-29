import io.ktor.application.install
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.websocket.WebSockets
import io.netty.buffer.ByteBuf
import io.reactivex.Single
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.core.RSocketConnector
import io.rsocket.kotlin.core.RSocketServer
import io.rsocket.kotlin.transport.ktor.server.RSocketSupport
import io.rsocket.kotlin.transport.ktor.server.rSocket
import io.wisoft.test.Test
import io.wisoft.test.TestService
import io.wisoft.test.TestServiceClient
import io.wisoft.test.TestServiceServer

fun main() {
    val rSocketServer = RSocketServer()
    embeddedServer(CIO, port = 9000) {
        install(WebSockets)
        install(RSocketSupport) {
            server = rSocketServer
        }
        routing {
            rSocket("/") {
                RSocketRequestHandler {
                    requestResponse { request ->

                    }
                }
            }
        }
    }
}

class ServerAcceptor : TestService {

    override fun requestResponse(message: Test.Request, metadata: ByteBuf): Single<Test.Response> {
        return Single.defer {
            Single.just(response("server response"))
        }
    }
}

fun response(message: String) = Test.Response
    .newBuilder()
    .setMessage(message)
    .build()

fun request(message: String) = Test.Request
    .newBuilder()
    .setMessage(message)
    .build()
