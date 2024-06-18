package com.omersungur.data.mapper

import com.omersungur.data.remote.dto.JWTUserDto
import com.omersungur.domain.model.JWTUser

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