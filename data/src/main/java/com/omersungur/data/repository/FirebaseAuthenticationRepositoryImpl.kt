package com.omersungur.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import com.omersungur.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : FirebaseAuthenticationRepository {

    override suspend fun userUid(): String = auth.currentUser?.uid ?: ""

    override suspend fun isLoggedIn(): Boolean = auth.currentUser != null

    override suspend fun logout() = auth.signOut()

    override fun login(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())
            val data = auth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }

    override fun register(email: String, password: String): Flow<Resource<AuthResult>> =
        flow {
            try {
                emit(Resource.Loading())
                val data = auth.createUserWithEmailAndPassword(email, password).await()
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong."))
            }
        }

    override fun resetPassword(email: String): Flow<Resource<Void?>> = flow {
        try {
            emit(Resource.Loading())
            val data = auth.sendPasswordResetEmail(email).await()
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }
}
