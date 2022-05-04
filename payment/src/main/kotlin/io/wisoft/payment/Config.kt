package io.wisoft.payment

import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.netty.client.TcpClientTransport
import io.rsocket.rpc.kotlin.rsocket.RequestHandlingRSocket
import io.wisoft.test.TestService
import io.wisoft.test.TestServiceServer
import java.util.concurrent.TimeUnit
import org.springframework.context.annotation.Bean


@Bean
fun rSocket(): RSocket {
    return RSocketFactory
        .connect()
        .acceptor { { RequestHandlingRSocket(TestServiceServer(ClientAcceptor())) } }
        .transport(TcpClientTransport.create("localhost", 7000))
        .start()
        .timeout(5, TimeUnit.SECONDS)
        .blockingGet()
}

class ClientAcceptor : TestService
