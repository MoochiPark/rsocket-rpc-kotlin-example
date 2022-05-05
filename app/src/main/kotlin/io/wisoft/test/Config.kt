package io.wisoft.test

import io.netty.buffer.ByteBuf
import io.reactivex.Single
import io.rsocket.kotlin.Duration
import io.rsocket.kotlin.KeepAliveOptions
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.netty.client.TcpClientTransport
import io.rsocket.rpc.kotlin.rsocket.RequestHandlingRSocket
import java.util.concurrent.TimeUnit

fun rSocket(): RSocket = RSocketFactory
    .connect()
//    .keepAlive { it.keepAliveInterval(Duration.ofSeconds(5)) }
    .acceptor { { RequestHandlingRSocket(PaymentServiceServer(ClientAcceptor())) } }
    .transport(TcpClientTransport.create("localhost", 7000))
    .start()
    .timeout(5, TimeUnit.SECONDS)
    .blockingGet()

fun paymentClient(rSocket: RSocket): PaymentServiceClient = PaymentServiceClient(rSocket)


class ServerAcceptor(private val paymentServiceClient: PaymentServiceClient) : PaymentService {

    override fun requestResponse(message: Payment.PaymentRequest, metadata: ByteBuf): Single<Payment.PaymentResponse> {
        return Single.defer {
            Single.just(paymentResponse(true))
        }
    }
}

private class ClientAcceptor : PaymentService

fun paymentResponse(isSuccess: Boolean): Payment.PaymentResponse = Payment.PaymentResponse
    .newBuilder()
    .setIsSuccess(isSuccess)
    .build()

fun paymentRequest(userId: Long, productId: Long): Payment.PaymentRequest = Payment.PaymentRequest
    .newBuilder()
    .setUserId(userId)
    .setProductId(productId)
    .build()
