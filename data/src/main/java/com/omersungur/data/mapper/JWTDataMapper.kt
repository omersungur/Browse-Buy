package com.omersungur.data.mapper

import com.omersungur.data.remote.dto.jwt_user.JWTDataDto
import com.omersungur.domain.model.jwt_user.JWTData

fun JWTDataDto.toJWTData(): JWTData {
    return JWTData(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        token = token,
    )
}