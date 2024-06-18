package com.omersungur.domain.use_cases.auth.register

import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
) {

    operator fun invoke(email: String, password: String) = firebaseAuthenticationRepository.register(email, password)
}
