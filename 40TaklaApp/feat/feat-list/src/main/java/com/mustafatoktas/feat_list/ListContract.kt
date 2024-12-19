package com.mustafatoktas.feat_list

import com.dokar.sonner.ToastType
import com.mustafatoktas.core_domain.model.TaklaciUser
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object ListContract {

    data class UiState(
        val isRefreshing: Boolean = false,
        val usersList: List<TaklaciUser> = emptyList(),
        val isLoading: Boolean = true,
        val currentUserID: String = String(),
    )

    sealed class UiAction {
        data object OnClickUserRowItem : UiAction()
        data object OnClickRefreshButton : UiAction()
        data object OnRefresh : UiAction()
    }

    sealed class UiEffect {
        data class ShowSnackBar(
            val message: String = String(),
            val duration : Duration = 2_700.milliseconds,
            val type : ToastType = ToastType.Success,
        ) : UiEffect()
    }
}
