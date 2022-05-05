package io.wisoft.test

class ShoppingService(
    private val productRepository: ProductRepository = ProductRepository(),
) {
    fun payment(dto: Request.Payment): Response.Result {
        if (productRepository.isInStock(dto.productId)) {
            val response: Payment.PaymentResponse =
                paymentClient(rSocket())
                    .requestResponse(paymentRequest(dto.userId, dto.productId))
                    .blockingGet()
            if (response.isSuccess) {
                return Response.Result("결제가 성공했습니다.")
            }
        }
        return Response.Result("결제가 실패했습니다.")
    }
}
