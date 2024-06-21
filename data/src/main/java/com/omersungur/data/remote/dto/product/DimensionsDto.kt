package com.omersungur.data.remote.dto.product


import com.google.gson.annotations.SerializedName

data class DimensionsDto(
    @SerializedName("depth")
    val depth: Double?,
    @SerializedName("height")
    val height: Double?,
    @SerializedName("width")
    val width: Double?
)