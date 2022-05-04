package io.wisoft.test

data class Product(
    val id: Long,
    private val name: String,
    private val price: Long,
    val stock: Long,
)
