package com.omersungur.data.remote.dto.cart


import com.google.gson.annotations.SerializedName

data class CartXDto(
    @SerializedName("discountedTotal")
    val discountedTotal: Double?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("products")
    val products: List<ProductCartDto>?,
    @SerializedName("total")
    val total: Double?,
    @SerializedName("totalProducts")
    val totalProducts: Int?,
    @SerializedName("totalQuantity")
    val totalQuantity: Int?,
    @SerializedName("userId")
    val userId: Int?
)