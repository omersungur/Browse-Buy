package com.omersungur.domain.repository.auth

import com.google.firebase.auth.AuthResult
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthenticationRepository {

    fun login(email: String, password: String): Flow<Resource<AuthResult>>

    fun register(email: String, password: String): Flow<Resource<AuthResult>>

    fun resetPassword(email: String): Flow<Resource<Void?>>

    suspend fun logout()

    suspend fun userUid(): String

    suspend fun isLoggedIn(): Boolean
}
