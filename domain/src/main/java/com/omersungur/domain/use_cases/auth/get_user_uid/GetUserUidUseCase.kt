package com.omersungur.domain.use_cases.auth.get_user_uid

import com.omersungur.domain.repository.auth.FirebaseAuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUidUseCase @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
) {

    suspend operator fun invoke() = flow { emit(firebaseAuthenticationRepository.userUid()) }
}
