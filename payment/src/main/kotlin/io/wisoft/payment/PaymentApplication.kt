package io.wisoft.payment

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.netty.server.TcpServerTransport
import io.wisoft.test.PaymentServiceClient
import io.wisoft.test.PaymentServiceServer
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    embeddedServer(Netty, host = "0.0.0.0", port = 7000) {}.start(wait = true)
}

val server: Disposable = RSocketFactory.receive()
    .acceptor {
        { _, rSocket: RSocket ->
            Single.just(
                PaymentServiceServer(ServerAcceptor(PaymentServiceClient(rSocket)))
            )
        }
    }.transport(TcpServerTransport.create("localhost", 7000))
    .start()
    .timeout(5, TimeUnit.SECONDS)
    .subscribe()
