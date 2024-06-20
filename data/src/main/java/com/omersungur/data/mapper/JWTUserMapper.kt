package com.omersungur.data.mapper

import com.omersungur.data.remote.dto.jwt_user.JWTUserDto
import com.omersungur.domain.model.jwt_user.JWTUser

fun JWTUserDto.toJWTUser(): JWTUser {
    return JWTUser(
        username = username,
        password = password,
    )
}

fun JWTUser.toJWTUserDto(): JWTUserDto {
    return JWTUserDto(
        username = username,
        password = password,
    )
}