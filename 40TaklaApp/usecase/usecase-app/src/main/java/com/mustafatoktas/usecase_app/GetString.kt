package com.mustafatoktas.usecase_app

import android.content.Context
import javax.inject.Inject

class GetString @Inject constructor(
    private val context: Context,
) {
    operator fun invoke(id: Int): String {
        return context.getString(id)
    }
}
