package com.omersungur.data.repository.user

import com.omersungur.data.mapper.toUser
import com.omersungur.data.mapper.toUserDto
import com.omersungur.data.remote.ProductApi
import com.omersungur.domain.model.user.User
import com.omersungur.domain.repository.user.UserRepository
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
) : UserRepository {

    override fun getUserInfo(userId: Int): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val userInfo = productApi.getUserInfo(userId)
            emit(Resource.Success(userInfo.toUser()))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage ?: "Error"))
        }
    }

    override fun updateUser(userId: Int, user: User): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val userInfo = productApi.updateUser(userId, user.toUserDto())
            emit(Resource.Success(userInfo.toUser()))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage ?: "Error"))
        }
    }
}