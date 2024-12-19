package com.mustafatoktas.usecase_firebase

import com.mustafatoktas.core_domain.repository.FirebaseRepository
import javax.inject.Inject

class GetCurrentUserID @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(): String =
        firebaseRepository.getCurrentUserID()

}
