package com.mustafatoktas.core_domain.repository

import com.mustafatoktas.core_domain.model.TaklaciUser

interface FirebaseRepository {
    suspend fun signInAnonymously(): String
    suspend fun getAllUsers(): List<TaklaciUser>
    suspend fun getUserNameSurnameByID(userID: String): String
    suspend fun isUserLoggedIn(): Boolean
    suspend fun setNameSurname(userID: String, nameSurname: String)
    suspend fun getCurrentUserID(): String
}
