package io.wisoft.test

import kotlinx.serialization.Serializable

object Request {

    @Serializable
    data class Payment(
        val userId: Long,
        val productId: Long,
    )

}

object Response {

    @Serializable
    data class Result(
        val message: String,
    )

}
