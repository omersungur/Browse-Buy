package com.omersungur.data.mapper

import com.omersungur.data.remote.dto.JWTDataDto
import com.omersungur.domain.model.JWTData

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