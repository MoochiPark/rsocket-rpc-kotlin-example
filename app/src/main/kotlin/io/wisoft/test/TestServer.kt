package io.wisoft.test

import io.netty.buffer.ByteBuf
import io.reactivex.Flowable
import io.reactivex.Single
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.netty.client.TcpClientTransport
import io.rsocket.kotlin.transport.netty.server.TcpServerTransport
import io.rsocket.rpc.kotlin.rsocket.RequestHandlingRSocket
import java.util.concurrent.TimeUnit

fun main() {
    println("1")
    val context = RSocketFactory.receive()
        .acceptor {
            { _, rSocket ->
                Single.just(
                    TestServiceServer(ServerAcceptor(TestServiceClient(rSocket)))
                )
            }
//        }.transport(TcpServerTransport.create("localhost", 9000))
        }.transport(TcpServerTransport.create("localhost", 9000))
        .start()
        .timeout(5, TimeUnit.SECONDS)
        .blockingGet()

    println("2")
    val rSocket = RSocketFactory
        .connect()
        .acceptor { { RequestHandlingRSocket(TestServiceServer(ClientAcceptor())) } }
        .transport(TcpClientTransport.create(context.address()))
        .start()
        .timeout(5, TimeUnit.SECONDS)
        .blockingGet()

//    val rSocket = RSocketFactory
//        .connect()
//        .transport(TcpClientTransport.create("localhost", 9000))
//        .start()
//        .blockingGet()

    val testClient = TestServiceClient(rSocket)

    val request = request("client: io.wisoft.test.request-io.wisoft.test.response")

    val response: Test.Response = testClient.requestResponse(request).blockingGet()

    println(response.message)

//    println("3")
//    val requestResponse =
//        Flowable.interval(3, TimeUnit.SECONDS)
//            .onBackpressureDrop()
//            .flatMapSingle { testClient.requestResponse(request("client: io.wisoft.test.request-io.wisoft.test.response")) }
//
//    println(requestResponse)
//     Flowable.merge(Flowable.just(requestResponse))
//        .ignoreElements()
//        .blockingAwait()
//    println("4")
}

class ServerAcceptor(private val testServiceClient: TestServiceClient) : TestService {

    override fun requestResponse(message: Test.Request, metadata: ByteBuf): Single<Test.Response> {
        println(message)
        println(metadata)
        return Single.defer {
            Single.just(response("server: io.wisoft.test.response"))
        }
    }
}

class ClientAcceptor : TestService {

}

fun response(message: String) = Test.Response
    .newBuilder()
    .setMessage(message)
    .build()

fun request(message: String) = Test.Request
    .newBuilder()
    .setMessage(message)
    .build()
