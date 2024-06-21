package com.omersungur.data.remote.dto.product


import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("products")
    val products: List<ProductXDto>?,
    @SerializedName("skip")
    val skip: Int?,
    @SerializedName("total")
    val total: Int?
)