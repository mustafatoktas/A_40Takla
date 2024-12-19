package com.mustafatoktas.core_data.repository_impl

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mustafatoktas.core_domain.model.TaklaciUser
import com.mustafatoktas.core_domain.repository.FirebaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : FirebaseRepository {

    override suspend fun isUserLoggedIn(): Boolean =
        auth.currentUser != null

    override suspend fun getCurrentUserID(): String =
        auth.currentUser?.uid ?: throw Exception("No current user ID found")

    override suspend fun signInAnonymously(): String {
        val result = auth.signInAnonymously().await()
        val userID = result.user?.uid ?: throw Exception("User ID not found")
        val map = mapOf(
            "userID" to userID,
        )
        firestore.collection("users").document(userID)
            .set(map)
            .await()
        return userID
    }

    override suspend fun getAllUsers(): List<TaklaciUser> {
        val snapshot = firestore.collection("users")
            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .get()
            .await()
        return snapshot.documents.mapNotNull { document ->
            TaklaciUser(
                userID = document.getString("userID") ?: document.id,
                isimSoyisim = document.getString("isimSoyisim"),
                timeStamp = document.getTimestamp("timeStamp"),
            )
        }
    }

    override suspend fun getUserNameSurnameByID(userID: String): String {
        val document = firestore.collection("users")
            .document(userID)
            .get()
            .await()
        return document.getString("isimSoyisim") ?: throw Exception("Name not found")
    }

    override suspend fun setNameSurname(userID: String, nameSurname: String) {
        val map = mapOf(
            "isimSoyisim" to nameSurname,
            "timeStamp" to Timestamp.now(),
        )
        firestore.collection("users").document(userID).update(map).await()
    }
}
