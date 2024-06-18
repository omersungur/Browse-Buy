package com.omersungur.domain.use_cases.auth.login

import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
) {

    operator fun invoke(email: String, password: String) = firebaseAuthenticationRepository.login(email, password)
}
