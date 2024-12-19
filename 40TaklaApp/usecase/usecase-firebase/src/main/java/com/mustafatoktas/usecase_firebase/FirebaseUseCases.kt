package com.mustafatoktas.usecase_firebase

data class FirebaseUseCases(
    val signInAnonymously: SignInAnonymously,
    val getAllUsers: GetAllUsers,
    val getUserNameSurnameByID: GetUserNameSurnameByID,
    val isUserLoggedIn: IsUserLoggedIn,
    val setNameSurname: SetNameSurname,
    val getCurrentUserID: GetCurrentUserID,
)
