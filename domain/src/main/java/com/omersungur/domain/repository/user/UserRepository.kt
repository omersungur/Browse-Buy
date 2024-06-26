package com.omersungur.domain.repository.user

import com.omersungur.domain.model.user.User
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserInfo(userId: Int): Flow<Resource<User>>

    fun updateUser(userId: Int, user: User): Flow<Resource<User>>
}