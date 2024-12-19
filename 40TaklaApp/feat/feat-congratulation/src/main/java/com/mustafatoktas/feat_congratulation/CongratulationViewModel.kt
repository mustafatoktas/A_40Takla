package com.mustafatoktas.feat_congratulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokar.sonner.ToastType
import com.mustafatoktas.core_common.Resource
import com.mustafatoktas.core_ui.Destination
import com.mustafatoktas.usecase_app.AppUseCases
import com.mustafatoktas.usecase_firebase.FirebaseUseCases
import com.mustafatoktas.feat_congratulation.CongratulationContract.UiAction
import com.mustafatoktas.feat_congratulation.CongratulationContract.UiEffect
import com.mustafatoktas.feat_congratulation.CongratulationContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class CongratulationViewModel @Inject constructor(
    private val firebaseUseCases: FirebaseUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    private fun updateUiState(block: UiState.() -> UiState) = _uiState.update(block)
    private suspend fun emitUiEffect(uiEffect: UiEffect) = _uiEffect.send(uiEffect)

    init {
        fetchInitialNameSurname()
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.ChangeRefreshState -> changeRefreshState(uiAction.isRefreshing)
            is UiAction.ChangeDialogVisibility -> toggleDialogVisibility()
            is UiAction.ChangeNameSurname -> updateNameSurname(uiAction.nameSurname)
            is UiAction.SetNameSurnameToFirebase -> isUserLoggedIn()
            is UiAction.OnRefresh -> restartApp()
            is UiAction.OnClickNavigate -> navigateScreen(uiAction.destination)
        }
    }

    private fun fetchInitialNameSurname() {
        viewModelScope.launch {
            if (firebaseUseCases.isUserLoggedIn.invoke()) {
                handleFetchInitialNameSurname(firebaseUseCases.getCurrentUserID.invoke())
            }
        }
    }

    private suspend fun handleFetchInitialNameSurname(currentUserID : String) {
        when ( val result = firebaseUseCases.getUserNameSurnameByID(currentUserID)) {
            is Resource.Success -> updateUiState { copy(isListeyeKatilButtonEnabled = false, isDialogVisible = false, nameSurname = result.data) }
            is Resource.Error -> {}
        }
    }

    private fun changeRefreshState(isRefreshing: Boolean) {
        updateUiState { copy(isRefreshing = isRefreshing) }
    }

    private fun toggleDialogVisibility() {
        updateUiState { copy(isDialogVisible = !isDialogVisible) }
    }

    private fun updateNameSurname(nameSurname: String) {
        if (nameSurname.length > 30) return
        updateUiState { copy(nameSurname = nameSurname) }
    }

    private fun navigateScreen(destination: Destination) {
        viewModelScope.launch {
            emitUiEffect(UiEffect.NavigateScreen(destination))
            updateUiState { copy(isGoToListScreen = true) }
        }
    }

    private fun isUserLoggedIn() {
        viewModelScope.launch(Dispatchers.IO) {
            if (firebaseUseCases.isUserLoggedIn.invoke()) {
                setNameSurnameToFirebase()
            } else {
                anonymousSignInAndSetNameSurname()
            }
        }
    }

    private suspend fun setNameSurnameToFirebase() {
        val currentUser = firebaseUseCases.getCurrentUserID.invoke()
        val result = firebaseUseCases.setNameSurname(
            userID = currentUser,
            nameSurname = uiState.value.nameSurname,
            successMessage = "Başarıyla kaydedildi!",
        )
        handleSetNameSurnameToFirebase(result)
    }

    private suspend fun anonymousSignInAndSetNameSurname() {
        val signInResult = firebaseUseCases.signInAnonymously.invoke("İnternete bağlı değilsiniz!")
        when (signInResult) {
            is Resource.Success -> setNameSurnameToFirebase()
            is Resource.Error -> showSnackBar(signInResult.message, ToastType.Error, isHaveButton = true, duration = 5_000.milliseconds)
        }
    }

    private fun handleSetNameSurnameToFirebase(result: Resource<String>) {
        when (result) {
            is Resource.Success -> {
                updateUiState { copy(isListeyeKatilButtonEnabled = false) }
                showSnackBar(result.data, ToastType.Success)
            }
            is Resource.Error -> showSnackBar(result.message, ToastType.Error, isHaveButton = true, duration = 5_000.milliseconds)
        }
    }

    private fun showSnackBar(
        message: String,
        type : ToastType = ToastType.Success,
        isHaveButton: Boolean = false,
        duration: Duration = 2_700.milliseconds,
    ) {
        viewModelScope.launch {
            emitUiEffect(UiEffect.ShowSnackBar(message = message, type = type, isHaveButton = isHaveButton, duration = duration))
        }
    }

    private fun restartApp() {
        viewModelScope.launch(Dispatchers.IO) {
            emitUiEffect(UiEffect.RestartApp)
        }
    }
}

