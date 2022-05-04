package io.wisoft.payment

import io.netty.buffer.ByteBuf
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.netty.server.TcpServerTransport
import io.wisoft.test.Test
import io.wisoft.test.TestService
import io.wisoft.test.TestServiceClient
import io.wisoft.test.TestServiceServer
import java.util.concurrent.TimeUnit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentApplication

fun main(args: Array<String>) {
    runApplication<PaymentApplication>(*args)

}

val server: Disposable = RSocketFactory.receive()
    .acceptor {
        { _, rSocket: RSocket ->
            Single.just(
                TestServiceServer(ServerAcceptor(TestServiceClient(rSocket)))
            )
        }
    }.transport(TcpServerTransport.create("localhost", 7000))
    .start()
    .timeout(5, TimeUnit.SECONDS)
    .subscribe()


class ServerAcceptor(private val testServiceClient: TestServiceClient) : TestService {

    override fun requestResponse(message: Test.Request, metadata: ByteBuf): Single<Test.Response> {
        println(message)
        println(metadata)
        return Single.defer {
            Single.just(response("server: io.wisoft.test.response"))
        }
    }
}
