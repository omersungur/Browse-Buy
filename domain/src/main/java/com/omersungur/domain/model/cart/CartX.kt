package com.omersungur.domain.model.cart

data class CartX(
    val discountedTotal: Double?,
    val id: Int?,
    val products: List<ProductCart>?,
    val total: Double?,
    val totalProducts: Int?,
    val totalQuantity: Int?,
    val userId: Int?
)