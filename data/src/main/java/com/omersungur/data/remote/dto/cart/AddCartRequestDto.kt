package com.omersungur.data.remote.dto.cart

data class AddCartRequestDto(
    val userId: Int,
    val products: List<CartProductDto>
)