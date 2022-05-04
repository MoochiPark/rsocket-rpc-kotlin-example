package io.wisoft.test

class ProductRepository(
    private val products: Set<Product> = setOf(
        Product(1, "컵", 2000, 100),
        Product(2, "칫솔", 3000, 1),
        Product(3, "치약", 1500, 10),
    ),
) {
    fun getAll(): List<Product> = products.toList()
    fun getById(id: Long): Product? = products.find { it.id == id }
    fun isInStock(id: Long): Boolean = products.find { it.id == id }?.stock != 0L
}
