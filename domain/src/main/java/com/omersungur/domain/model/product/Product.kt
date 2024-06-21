package com.omersungur.domain.model.product

data class Product(
    val limit: Int?,
    val products: List<ProductX>?,
    val skip: Int?,
    val total: Int?
)