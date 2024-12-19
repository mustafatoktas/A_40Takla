package com.mustafatoktas.feat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokar.sonner.ToastType
import com.mustafatoktas.core_common.Resource
import com.mustafatoktas.core_domain.model.TaklaciUser
import com.mustafatoktas.usecase_app.AppUseCases
import com.mustafatoktas.usecase_firebase.FirebaseUseCases
import com.mustafatoktas.feat_list.ListContract.UiAction
import com.mustafatoktas.feat_list.ListContract.UiEffect
import com.mustafatoktas.feat_list.ListContract.UiState
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

@HiltViewModel
class ListViewModel @Inject constructor(
    private val firebaseUseCases: FirebaseUseCases,
    private val appUseCases: AppUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    private suspend fun emitUiEffect(uiEffect: UiEffect) = _uiEffect.send(uiEffect)
    private fun updateUiState(block: UiState.() -> UiState) = _uiState.update(block)

    init {
        getAllUsers()
        getCurrentUserID()
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            UiAction.OnRefresh -> refrehingToGetAllUsers()
            UiAction.OnClickUserRowItem -> onCLickUserRowItem()
            UiAction.OnClickRefreshButton -> getAllUsers()
        }
    }

    private fun onCLickUserRowItem() {
        showSnackBar(
            message = appUseCases.getString(R.string.tiklayinca_bir_sey_olmuyor),
            type = ToastType.Info,
        )
    }

    private fun getCurrentUserID() {
        viewModelScope.launch(Dispatchers.IO) {
            if (firebaseUseCases.isUserLoggedIn.invoke()) {
                val userID = firebaseUseCases.getCurrentUserID.invoke()
                updateUiState { copy( currentUserID = userID) }
            }
        }
    }

    private fun refrehingToGetAllUsers() {
        updateUiState { copy(isRefreshing = true) }
        getAllUsers(true)
        updateUiState { copy(isRefreshing = false) }
        showSnackBar(
            message = appUseCases.getString(R.string.guncellendi),
            type = ToastType.Success,
        )
    }

    private fun getAllUsers(fromRefreshing: Boolean = false) {
        if (!fromRefreshing)
            updateUiState { copy(isLoading = true) }
        viewModelScope.launch {
            val result = firebaseUseCases.getAllUsers.invoke()
            handleAllUsersResult(result)
        }
    }

    private fun handleAllUsersResult(result: Resource<List<TaklaciUser>>) {
            when (result) {
                is Resource.Success ->
                    updateUiState { copy(usersList = result.data, isLoading = false) }
                is Resource.Error -> {
                    updateUiState { copy(isLoading = false) }
                    showSnackBar(message = result.message, type =ToastType.Error )
                }
            }
    }

    private fun showSnackBar(message: String, type: ToastType) {
        viewModelScope.launch {
            emitUiEffect(UiEffect.ShowSnackBar(message = message, type = type))
        }
    }
}
