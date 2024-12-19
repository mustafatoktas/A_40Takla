package com.mustafatoktas.usecase_app

import android.content.Context
import javax.inject.Inject

class GetVersionNumber @Inject constructor(
    private val context: Context,
) {
    operator fun invoke(): String {
        val pm = context.packageManager
        val packageInfo = pm.getPackageInfo(context.packageName, 0)
        return context.getString(R.string.app_version, packageInfo.versionName)
    }
}