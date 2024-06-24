package com.omersungur.data.remote.dto.cart


import com.google.gson.annotations.SerializedName

data class CartDto(
    @SerializedName("carts")
    val carts: List<CartXDto>?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("skip")
    val skip: Int?,
    @SerializedName("total")
    val total: Int?
)