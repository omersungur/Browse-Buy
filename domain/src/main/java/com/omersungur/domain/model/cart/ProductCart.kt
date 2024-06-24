package com.omersungur.domain.model.cart

data class ProductCart(
    val discountPercentage: Double?,
    val discountedTotal: Double?,
    val id: Int?,
    val price: Double?,
    val quantity: Int?,
    val thumbnail: String?,
    val title: String?,
    val total: Double?
)