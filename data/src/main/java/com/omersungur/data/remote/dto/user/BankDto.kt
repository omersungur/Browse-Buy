package com.omersungur.data.remote.dto.user


import com.google.gson.annotations.SerializedName

data class BankDto(
    @SerializedName("cardExpire")
    val cardExpire: String?,
    @SerializedName("cardNumber")
    val cardNumber: String?,
    @SerializedName("cardType")
    val cardType: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("iban")
    val iban: String?
)