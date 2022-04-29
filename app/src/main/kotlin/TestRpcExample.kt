import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.websocket.WebSockets
import io.netty.buffer.ByteBuf
import io.reactivex.Flowable
import io.reactivex.Single
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.ktor.client.RSocketSupport
import io.rsocket.kotlin.transport.ktor.client.rSocket
import io.rsocket.kotlin.transport.netty.server.TcpServerTransport
import io.wisoft.test.Test.Request
import io.wisoft.test.Test.Response
import io.wisoft.test.TestService
import io.wisoft.test.TestServiceClient
import io.wisoft.test.TestServiceServer
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking

val client = HttpClient(CIO) {
    install(WebSockets)
    install(RSocketSupport)
}

fun main() = runBlocking {
    RSocketFactory.receive()
        .acceptor {
            { _, rSocket ->
                Single.just(
                    TestServiceServer(ServerAcceptor(TestServiceClient(rSocket))))
            }
        }.transport(TcpServerTransport.create("localhost", 9000))
        .start()
        .timeout(5, TimeUnit.SECONDS)
        .blockingGet()

    val rSocket = client.rSocket("ws://localhost:9000/")
    val testClient = TestServiceClient(rSocket)

    val requestResponse =
        Flowable.interval(3, TimeUnit.SECONDS)
            .onBackpressureDrop()
            .flatMapSingle { testClient.requestResponse(request("client: hi")) }

    Flowable.merge(Flowable.just(requestResponse))
        .ignoreElements()
        .blockingAwait()
}
