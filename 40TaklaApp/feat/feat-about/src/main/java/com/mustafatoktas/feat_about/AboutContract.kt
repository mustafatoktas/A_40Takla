package com.mustafatoktas.feat_about

object AboutContract {

    data class UiState(
        val appVersion: String = String(),
        val phoneModel: String = String(),
        val osVersion: String = String(),
    )
}