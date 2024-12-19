package com.mustafatoktas.usecase_app

import android.content.Context
import android.os.Build
import javax.inject.Inject

class GetOSName @Inject constructor(
    private val context: Context,
) {
    operator fun invoke(): String {
        val versionName = Build.VERSION.RELEASE
        val sdkVersion = Build.VERSION.SDK_INT
        return context.getString(R.string.android_version, versionName, sdkVersion)
    }
}