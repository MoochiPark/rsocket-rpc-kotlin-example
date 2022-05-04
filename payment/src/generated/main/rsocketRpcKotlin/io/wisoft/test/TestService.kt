package io.wisoft.test

/**
 */
@javax.annotation.Generated(
    value = ["by RSocket RPC proto compiler"],
    comments = "Source: io.wisoft.test/test.proto")
interface TestService {

  companion object {
    const val SERVICE = "io.wisoft.test.TestService"
    const val METHOD_REQUEST_RESPONSE = "RequestResponse"
  }

  /**
   */
  fun requestResponse(message: io.wisoft.test.Test.Request, metadata: io.netty.buffer.ByteBuf)
  :io.reactivex.Single<io.wisoft.test.Test.Response> = io.reactivex.Single.error(UnsupportedOperationException("not implemented"))
}
