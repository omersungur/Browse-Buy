package com.omersungur.domain.repository.auth

import com.omersungur.domain.model.jwt_user.JWTData
import com.omersungur.domain.model.jwt_user.JWTUser
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface JWTAuthRepository {

    fun login(jwtUser: JWTUser): Flow<Resource<JWTData>>
}