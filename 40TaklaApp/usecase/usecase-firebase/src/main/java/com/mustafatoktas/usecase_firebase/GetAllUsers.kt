package com.mustafatoktas.usecase_firebase

import com.mustafatoktas.core_common.Resource
import com.mustafatoktas.core_domain.model.TaklaciUser
import com.mustafatoktas.core_domain.repository.FirebaseRepository
import javax.inject.Inject

class GetAllUsers @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(errorMessage : String = String()): Resource<List<TaklaciUser>> =
        try {
            val users = firebaseRepository.getAllUsers()
            Resource.Success(users)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: errorMessage)
        }

}
