package io.wisoft.test

class ShoppingService(
    private val productRepository: ProductRepository = ProductRepository(),
) {
    fun payment(dto: Request.Payment) {
        if (productRepository.isInStock(dto.productId)) {
            println("재고 있음")
        }
    }
}
