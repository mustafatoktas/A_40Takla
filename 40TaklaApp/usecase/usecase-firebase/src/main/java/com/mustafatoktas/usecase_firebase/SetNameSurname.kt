package com.mustafatoktas.usecase_firebase

import com.mustafatoktas.core_common.Resource
import com.mustafatoktas.core_domain.repository.FirebaseRepository
import javax.inject.Inject

class SetNameSurname @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(
        userID: String,
        nameSurname: String,
        errorMessage: String = String(),
        successMessage: String = String(),
    ): Resource<String> =
        try {
            firebaseRepository.setNameSurname(userID, nameSurname)
            Resource.Success(successMessage)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: errorMessage)
        }

}
