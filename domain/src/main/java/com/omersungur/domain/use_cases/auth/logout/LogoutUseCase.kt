package com.omersungur.domain.use_cases.auth.logout

import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
) {

    suspend operator fun invoke() = firebaseAuthenticationRepository.logout()
}
