package com.omersungur.data.remote.dto.cart


import com.google.gson.annotations.SerializedName

data class ProductCartDto(
    @SerializedName("discountPercentage")
    val discountPercentage: Double?,
    @SerializedName("discountedTotal")
    val discountedTotal: Double?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("total")
    val total: Double?
)