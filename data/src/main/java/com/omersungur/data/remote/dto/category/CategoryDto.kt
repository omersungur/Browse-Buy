package com.omersungur.data.remote.dto.category

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("url")
    val url: String?
)