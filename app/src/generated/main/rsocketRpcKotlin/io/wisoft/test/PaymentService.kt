package io.wisoft.test

/**
 */
@javax.annotation.Generated(
    value = ["by RSocket RPC proto compiler"],
    comments = "Source: io.wisoft.test/payment.proto")
interface PaymentService {

  companion object {
    const val SERVICE = "io.wisoft.test.PaymentService"
    const val METHOD_REQUEST_RESPONSE = "RequestResponse"
  }

  /**
   */
  fun requestResponse(message: io.wisoft.test.Payment.PaymentRequest, metadata: io.netty.buffer.ByteBuf)
  :io.reactivex.Single<io.wisoft.test.Payment.PaymentResponse> = io.reactivex.Single.error(UnsupportedOperationException("not implemented"))
}
