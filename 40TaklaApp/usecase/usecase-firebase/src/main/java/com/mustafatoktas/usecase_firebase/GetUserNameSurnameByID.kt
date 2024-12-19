package com.mustafatoktas.usecase_firebase

import com.mustafatoktas.core_common.Resource
import com.mustafatoktas.core_domain.repository.FirebaseRepository
import javax.inject.Inject

class GetUserNameSurnameByID @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(userID: String, errorMessage : String = String()): Resource<String> =
        try {
            val nameSurname = firebaseRepository.getUserNameSurnameByID(userID)
            Resource.Success(nameSurname)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: errorMessage)
        }

}
