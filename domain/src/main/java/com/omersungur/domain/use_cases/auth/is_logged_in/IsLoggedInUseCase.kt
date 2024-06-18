package com.omersungur.domain.use_cases.auth.is_logged_in

import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
) {

    operator fun invoke() = flow { emit(firebaseAuthenticationRepository.isLoggedIn()) }
}
