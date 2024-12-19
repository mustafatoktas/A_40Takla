package com.mustafatoktas.kirktakla.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mustafatoktas.core_data.repository_impl.FirebaseRepositoryImpl
import com.mustafatoktas.core_domain.repository.FirebaseRepository
import com.mustafatoktas.usecase_firebase.FirebaseUseCases
import com.mustafatoktas.usecase_firebase.GetAllUsers
import com.mustafatoktas.usecase_firebase.GetCurrentUserID
import com.mustafatoktas.usecase_firebase.GetUserNameSurnameByID
import com.mustafatoktas.usecase_firebase.IsUserLoggedIn
import com.mustafatoktas.usecase_firebase.SetNameSurname
import com.mustafatoktas.usecase_firebase.SignInAnonymously
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): FirebaseRepository =
        FirebaseRepositoryImpl(auth = auth, firestore = firestore)

    @Provides
    @Singleton
    fun provideFirebaseUseCases(firebaseRepository: FirebaseRepository): FirebaseUseCases =
        FirebaseUseCases(
            signInAnonymously = SignInAnonymously(firebaseRepository),
            getAllUsers = GetAllUsers(firebaseRepository),
            getUserNameSurnameByID = GetUserNameSurnameByID(firebaseRepository),
            isUserLoggedIn = IsUserLoggedIn(firebaseRepository),
            setNameSurname = SetNameSurname(firebaseRepository),
            getCurrentUserID = GetCurrentUserID(firebaseRepository),
        )
}
