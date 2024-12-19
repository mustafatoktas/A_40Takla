package com.mustafatoktas.feat_congratulation

import com.dokar.sonner.ToastType
import com.mustafatoktas.core_ui.Destination
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object CongratulationContract {

    data class UiState(
        val isRefreshing: Boolean = false,
        val isDialogVisible: Boolean = false,
        val nameSurname: String = String(),
        val isListeyeKatilButtonEnabled: Boolean = true,
        val isGoToListScreen: Boolean = false,
    )

    sealed class UiAction {
        data class OnClickNavigate(
            val destination: Destination,
        ) : UiAction()
        data object OnRefresh : UiAction()
        data class ChangeRefreshState(val isRefreshing: Boolean) : UiAction()
        data object ChangeDialogVisibility : UiAction()
        data class ChangeNameSurname(val nameSurname: String) : UiAction()
        data object SetNameSurnameToFirebase : UiAction()
    }

    sealed class UiEffect {
        data class NavigateScreen(
            val destination: Destination,
        ) : UiEffect()
        data object RestartApp : UiEffect()
        data class ShowSnackBar(
            val message: String = String(),
            val isHaveButton: Boolean = false,
            val duration : Duration = 2_700.milliseconds,
            val type : ToastType = ToastType.Success,
        ) : UiEffect()
    }
}
