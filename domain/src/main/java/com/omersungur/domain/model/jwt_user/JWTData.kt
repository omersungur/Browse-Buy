package com.omersungur.domain.model.jwt_user

data class JWTData (
    val id: Long?,
    val username: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val gender: String?,
    val image: String?,
    val token: String?,
)