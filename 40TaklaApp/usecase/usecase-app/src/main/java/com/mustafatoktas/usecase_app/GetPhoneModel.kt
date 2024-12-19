package com.mustafatoktas.usecase_app

import android.os.Build

class GetPhoneModel {
    operator fun invoke(): String {
        return Build.MODEL
    }
}
