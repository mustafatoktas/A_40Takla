package com.mustafatoktas.usecase_firebase

import com.mustafatoktas.core_common.Resource
import com.mustafatoktas.core_domain.repository.FirebaseRepository
import javax.inject.Inject

class SignInAnonymously @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(errorMessage : String = String()): Resource<String> =
        try {
            val userID = firebaseRepository.signInAnonymously()
            Resource.Success(userID)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: errorMessage)
        }

}
