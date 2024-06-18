package com.omersungur.domain.use_cases.auth.reset_password

import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
) {

    operator fun invoke(email: String) = firebaseAuthenticationRepository.resetPassword(email)
}
