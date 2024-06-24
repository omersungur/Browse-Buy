package com.omersungur.domain.model.cart

data class Cart(
    val carts: List<CartX>?,
    val limit: Int?,
    val skip: Int?,
    val total: Int?
)