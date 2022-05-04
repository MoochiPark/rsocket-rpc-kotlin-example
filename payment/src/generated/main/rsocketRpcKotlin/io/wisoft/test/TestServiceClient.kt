package io.wisoft.test

@javax.annotation.Generated(
    value = ["by RSocket RPC proto compiler"],
    comments = "Source: io.wisoft.test/test.proto")
@io.rsocket.rpc.kotlin.annotations.Generated(
    type = io.rsocket.rpc.kotlin.annotations.ResourceType.CLIENT,
    idlClass = TestService::class)
class TestServiceClient(
  private val rSocket: io.rsocket.kotlin.RSocket, 
  private val tracer: io.opentracing.Tracer? = null
  ): TestService {
    private val requestResponseTrace: (Map<String, String>) -> io.reactivex.FlowableTransformer<io.wisoft.test.Test.Response, io.wisoft.test.Test.Response>

    init {
      when(tracer) {
        null -> {
          this.requestResponseTrace = io.rsocket.rpc.kotlin.tracing.Tracing.trace()
        }
        else -> {
          this.requestResponseTrace = io.rsocket.rpc.kotlin.tracing.Tracing.trace(
            tracer,
            TestService.METHOD_REQUEST_RESPONSE,
            io.rsocket.rpc.kotlin.tracing.Tag.of("rsocket.service", TestService.SERVICE),
            io.rsocket.rpc.kotlin.tracing.Tag.of("rsocket.type", "client"),
            io.rsocket.rpc.kotlin.tracing.Tag.of("rsocket.version", "")
          )
        }
      }
    }

    @io.rsocket.rpc.kotlin.annotations.GeneratedMethod(returnTypeClass = io.wisoft.test.Test.Response::class)
    fun requestResponse(message: io.wisoft.test.Test.Request): io.reactivex.Single<io.wisoft.test.Test.Response> {
      return requestResponse(message, io.netty.buffer.Unpooled.EMPTY_BUFFER)
    }

    @io.rsocket.rpc.kotlin.annotations.GeneratedMethod(returnTypeClass = io.wisoft.test.Test.Response::class)
    override fun requestResponse(message: io.wisoft.test.Test.Request, metadata: io.netty.buffer.ByteBuf)
      : io.reactivex.Single<io.wisoft.test.Test.Response> {
      val map: Map<String, String> = java.util.HashMap()
        return io.reactivex.Single.defer {
          val tracingMetadata = io.rsocket.rpc.kotlin.tracing.Tracing.mapToByteBuf(io.netty.buffer.ByteBufAllocator.DEFAULT, map)
          val metadataBuf = io.rsocket.rpc.kotlin.frames.Metadata.encode(io.netty.buffer.ByteBufAllocator.DEFAULT, TestService.SERVICE, TestService.METHOD_REQUEST_RESPONSE, tracingMetadata, metadata)
          val data = serialize(message)
          val payload = io.rsocket.kotlin.DefaultPayload(data, metadataBuf)
          tracingMetadata.release()
          metadataBuf.release()
          data.release()
          rSocket.requestResponse(payload)
        }
      .map(deserializer(io.wisoft.test.Test.Response.parser())).toFlowable().compose(requestResponseTrace(map)).firstOrError()
    }

    private fun serialize(message: com.google.protobuf.MessageLite):io.netty.buffer.ByteBuf {
      val length = message.serializedSize
      val byteBuf = io.netty.buffer.ByteBufAllocator.DEFAULT.buffer(length)
      try {
        message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.nioBuffer(0, length)))
        byteBuf.writerIndex(length)
        return byteBuf
      } catch (t: Throwable) {
        byteBuf.release()
        throw RuntimeException(t)
      }
    }

    private fun <T> deserializer(parser: com.google.protobuf.Parser<T>): (io.rsocket.kotlin.Payload) -> T =
      { payload -> parser.parseFrom(com.google.protobuf.CodedInputStream.newInstance(payload.data)) }
    }
