package com.omersungur.data.remote.dto.user


import com.google.gson.annotations.SerializedName

data class CompanyDto(
    @SerializedName("address")
    val address: AddressDto?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("title")
    val title: String?
)