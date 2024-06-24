package com.omersungur.data.remote.dto.user


import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("address")
    val address: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("coordinates")
    val coordinates: CoordinatesDto?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("postalCode")
    val postalCode: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("stateCode")
    val stateCode: String?
)