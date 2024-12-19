package com.mustafatoktas.usecase_app

data class AppUseCases(
    val getString: GetString,
    val getVersionName: GetVersionNumber,
    val getOSName: GetOSName,
    val getPhoneModel: GetPhoneModel,
)
