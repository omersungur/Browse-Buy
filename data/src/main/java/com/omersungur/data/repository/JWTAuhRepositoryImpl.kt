package com.omersungur.data.repository

import com.omersungur.data.mapper.toJWTData
import com.omersungur.data.mapper.toJWTUserDto
import com.omersungur.data.remote.ProductApi
import com.omersungur.domain.model.JWTData
import com.omersungur.domain.model.JWTUser
import com.omersungur.domain.repository.auth.JWTAuthRepository
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JWTAuhRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
) : JWTAuthRepository {

    override fun login(jwtUser: JWTUser): Flow<Resource<JWTData>> = flow {
        try {
            emit(Resource.Loading())
            val response = productApi.login(jwtUser.toJWTUserDto())
            emit(Resource.Success(response.toJWTData()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }
}