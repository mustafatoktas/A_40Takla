package com.mustafatoktas.feat_about

import androidx.lifecycle.ViewModel
import com.mustafatoktas.usecase_app.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.mustafatoktas.feat_about.AboutContract.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val appUseCases: AppUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private fun updateUiState(block: UiState.() -> UiState) = _uiState.update(block)

    init {
        initializing()
    }

    private fun initializing() =
        updateUiState { copy(appVersion = getVersiyonNumber(),
            phoneModel = getPhoneModelName(),
            osVersion = getOperationSystemName())
        }

    private fun getVersiyonNumber(): String {
        val versiyon = appUseCases.getVersionName.invoke()
        return versiyon.substring(1) // İlk karakteri atlar ve kalan kısmı döner
    }

    private fun getPhoneModelName(): String =
        appUseCases.getPhoneModel.invoke()

    private fun getOperationSystemName(): String =
        appUseCases.getOSName.invoke()

}
