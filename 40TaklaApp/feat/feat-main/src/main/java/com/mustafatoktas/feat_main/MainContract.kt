package com.mustafatoktas.feat_main

import androidx.compose.ui.graphics.Color
import com.dokar.sonner.ToastType
import com.mustafatoktas.core_common.DeviceStatus
import com.mustafatoktas.core_ui.theme.renk1
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object MainContract {

    data class UiState(
        val deviceStatus: DeviceStatus = DeviceStatus.Normal,
        val spinCount: Int = 0,
        val backgroundColor: Color = renk1,
        val backgroundColorIndex: Int = 0,
        val isAnimationPlaying: Boolean = false,
        )

    sealed class UiAction {
        data object GotoCongratulationScreen : UiAction()
        data object OnClickTaklaButton : UiAction()
        data object AnimationFinished : UiAction()
    }

    sealed class UiEffect {
        data object GoToCongratulationScreen : UiEffect()
        data object SpinAnimation : UiEffect()
        data class ShowSnackBar(
            val message: String = String(),
            val duration : Duration = 2_700.milliseconds,
            val type : ToastType = ToastType.Success,
        ) : UiEffect()
    }
}
