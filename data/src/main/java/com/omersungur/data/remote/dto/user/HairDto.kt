package com.omersungur.data.remote.dto.user


import com.google.gson.annotations.SerializedName

data class HairDto(
    @SerializedName("color")
    val color: String?,
    @SerializedName("type")
    val type: String?
)