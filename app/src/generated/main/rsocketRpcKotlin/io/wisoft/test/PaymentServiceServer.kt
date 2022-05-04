package io.wisoft.test;

@javax.annotation.Generated(
    value = ["by RSocket RPC proto compiler"],
    comments = "Source: io.wisoft.test/payment.proto")
@io.rsocket.rpc.kotlin.annotations.Generated(
    type = io.rsocket.rpc.kotlin.annotations.ResourceType.SERVICE,
    idlClass = PaymentService::class)
@javax.inject.Named(
    value ="PaymentServiceServer")
class PaymentServiceServer @javax.inject.Inject constructor(
  private val serviceImpl: PaymentService,
  private val tracer: io.opentracing.Tracer? = null)
  : io.rsocket.rpc.kotlin.AbstractRSocketService() {
  private val requestResponseTrace: (io.opentracing.SpanContext?) -> io.reactivex.FlowableTransformer<io.rsocket.kotlin.Payload, io.rsocket.kotlin.Payload>

  init {
    when(tracer) {
      null -> {
        this.requestResponseTrace = io.rsocket.rpc.kotlin.tracing.Tracing.traceAsChild()
      }
      else -> {
        this.requestResponseTrace = io.rsocket.rpc.kotlin.tracing.Tracing.traceAsChild(
          tracer,
          PaymentService.METHOD_REQUEST_RESPONSE,
          io.rsocket.rpc.kotlin.tracing.Tag.of("rsocket.service", PaymentService.SERVICE),
          io.rsocket.rpc.kotlin.tracing.Tag.of("rsocket.type", "server"),
          io.rsocket.rpc.kotlin.tracing.Tag.of("rsocket.version", "")
        )
      }
    }
  }

  override val service: String = PaymentService.SERVICE

  override val serviceClass = serviceImpl.javaClass

  override fun fireAndForget(payload: io.rsocket.kotlin.Payload):io.reactivex.Completable {
    return io.reactivex.Completable.error(UnsupportedOperationException("Fire and forget not implemented."))
  }

  override fun requestResponse(payload: io.rsocket.kotlin.Payload): io.reactivex.Single<io.rsocket.kotlin.Payload> {
    try {
      val metadata = io.netty.buffer.Unpooled.wrappedBuffer(payload.metadata)
      val spanContext = io.rsocket.rpc.kotlin.tracing.Tracing.deserializeTracingMetadata(tracer, metadata)
      val method = io.rsocket.rpc.kotlin.frames.Metadata.getMethod(metadata)
      return when (method) {
        PaymentService.METHOD_REQUEST_RESPONSE -> {
          val inputStream = com.google.protobuf.CodedInputStream.newInstance(payload.data)
          serviceImpl.requestResponse(io.wisoft.test.Payment.PaymentRequest.parseFrom(inputStream), metadata).map(serializer).toFlowable().compose(requestResponseTrace(spanContext)).firstOrError()
        }
        else -> io.reactivex.Single.error(UnsupportedOperationException())
      }
    } catch (t: Throwable) {
      return io.reactivex.Single.error(t)
    }
  }

  override fun requestStream(payload: io.rsocket.kotlin.Payload): io.reactivex.Flowable<io.rsocket.kotlin.Payload> {
    return io.reactivex.Flowable.error(UnsupportedOperationException("Request-Stream not implemented."))
  }

  override fun requestChannel(payload: io.rsocket.kotlin.Payload, publisher: io.reactivex.Flowable<io.rsocket.kotlin.Payload>): io.reactivex.Flowable<io.rsocket.kotlin.Payload> {
    return io.reactivex.Flowable.error(UnsupportedOperationException("Request-Channel not implemented."))
  }

  override fun requestChannel(payloads: org.reactivestreams.Publisher<io.rsocket.kotlin.Payload>): io.reactivex.Flowable<io.rsocket.kotlin.Payload> {
    return io.reactivex.Flowable.error(UnsupportedOperationException("Request-Channel not implemented."))
  }

  companion object {
    private val serializer:(com.google.protobuf.MessageLite) -> io.rsocket.kotlin.Payload = { message ->
      val length = message.serializedSize
      val byteBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.buffer(length)
      try {
        message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.nioBuffer(0, length)))
        byteBuf.writerIndex(length)
        io.rsocket.kotlin.DefaultPayload(byteBuf, io.netty.buffer.Unpooled.EMPTY_BUFFER)
      } catch (t: Throwable) {
        throw RuntimeException(t)
      } finally {
        byteBuf.release()
      }
    }

  private fun <T> deserializer(parser: com.google.protobuf.Parser<T>):(io.rsocket.kotlin.Payload) -> T {
    return { payload -> 
      try {
        val inputStream = com.google.protobuf.CodedInputStream.newInstance(payload.data)
        parser.parseFrom(inputStream)
      } catch (t: Throwable) {
        throw RuntimeException(t)
      }
    }
  }
}
}
