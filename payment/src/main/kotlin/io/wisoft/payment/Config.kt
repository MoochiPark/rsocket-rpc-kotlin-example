package io.wisoft.payment

import io.netty.buffer.ByteBuf
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.netty.client.TcpClientTransport
import io.rsocket.kotlin.transport.netty.server.TcpServerTransport
import io.rsocket.rpc.kotlin.rsocket.RequestHandlingRSocket
import io.wisoft.test.Payment
import io.wisoft.test.PaymentService
import io.wisoft.test.PaymentServiceClient
import io.wisoft.test.PaymentServiceServer
import io.wisoft.test.Test
import io.wisoft.test.TestService
import io.wisoft.test.TestServiceClient
import io.wisoft.test.TestServiceServer
import java.util.concurrent.TimeUnit

fun rSocket(): RSocket = RSocketFactory
    .connect()
    .acceptor { { RequestHandlingRSocket(PaymentServiceServer(ClientAcceptor())) } }
    .transport(TcpClientTransport.create("localhost", 7000))
    .start()
//    .timeout(5, TimeUnit.SECONDS)
    .blockingGet()

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
