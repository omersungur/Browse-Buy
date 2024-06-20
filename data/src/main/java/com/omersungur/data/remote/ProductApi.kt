package com.omersungur.data.remote

import com.omersungur.data.remote.dto.jwt_user.JWTDataDto
import com.omersungur.data.remote.dto.jwt_user.JWTUserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ProductApi {

    @POST("auth/login")
    suspend fun login(@Body jwtUser: JWTUserDto): JWTDataDto
}