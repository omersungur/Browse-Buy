package com.omersungur.domain.model.cart

data class AddCartRequest(
    val userId: Int,
    val products: List<CartProduct>
)