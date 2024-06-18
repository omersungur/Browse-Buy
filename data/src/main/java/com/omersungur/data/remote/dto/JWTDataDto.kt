package com.omersungur.data.remote.dto

data class JWTDataDto (
    val id: Long?,
    val username: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val gender: String?,
    val image: String?,
    val token: String?,
)