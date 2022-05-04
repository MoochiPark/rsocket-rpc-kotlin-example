package io.wisoft.payment

import io.reactivex.Single
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.netty.server.TcpServerTransport
import io.wisoft.test.Test
import io.wisoft.test.TestServiceClient
import io.wisoft.test.TestServiceServer
import java.util.concurrent.TimeUnit
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.annotation.ConnectMapping
import org.springframework.stereotype.Controller


fun response(message: String): Test.Response = Test.Response
    .newBuilder()
    .setMessage(message)
    .build()

fun request(message: String): Test.Request = Test.Request
    .newBuilder()
    .setMessage(message)
    .build()
