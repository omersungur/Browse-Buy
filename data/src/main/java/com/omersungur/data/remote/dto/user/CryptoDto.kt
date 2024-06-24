package com.omersungur.data.remote.dto.user


import com.google.gson.annotations.SerializedName

data class CryptoDto(
    @SerializedName("coin")
    val coin: String?,
    @SerializedName("network")
    val network: String?,
    @SerializedName("wallet")
    val wallet: String?
)