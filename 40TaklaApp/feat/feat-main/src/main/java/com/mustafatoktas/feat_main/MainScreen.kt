package com.mustafatoktas.feat_main

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.dokar.sonner.Toast
import com.dokar.sonner.rememberToasterState
import com.mustafatoktas.core_common.DeviceStatus
import com.mustafatoktas.core_common.navigateAndClearBackStack
import com.mustafatoktas.feat_main.MainContract.UiAction
import com.mustafatoktas.feat_main.MainContract.UiEffect
import com.mustafatoktas.feat_main.MainContract.UiState
import com.mustafatoktas.feat_main.composables.OtherContent
import com.mustafatoktas.core_ui.Destination
import com.mustafatoktas.core_ui.composables.TopSnackBar
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(
    uiState: UiState,
    mainNavController: NavHostController,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    when (uiState.deviceStatus) {
        DeviceStatus.Rooted -> OtherContent(
            baslik = stringResource(R.string.root_tespit_edildi),
            icerik = stringResource(R.string.root_icerigi),
            resim = R.drawable.img_root,
        )
        DeviceStatus.Emulator -> OtherContent(
            baslik = stringResource(R.string.cihaz_bir_emulator_uzerinde_calisiyor),
            icerik = stringResource(R.string.emulator_icerigi),
            resim = R.drawable.img_emulator,
        )
        DeviceStatus.Normal -> MainContent(
            mainNavController = mainNavController,
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = onAction,
        )
    }
}

@Composable
fun MainContent(
    uiState: UiState,
    mainNavController: NavHostController,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scale = remember { Animatable(1f) }
    val rotation = remember { Animatable(0f) }
    val toasterState = rememberToasterState()

    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        context.getSystemService(VibratorManager::class.java)?.defaultVibrator
    else context.getSystemService(Vibrator::class.java)


    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { uiEffect ->
                when (uiEffect) {
                    is UiEffect.GoToCongratulationScreen ->
                        mainNavController.navigateAndClearBackStack(Destination.CongratulationScreen)
                    is UiEffect.ShowSnackBar -> {
                        toasterState.show(
                            Toast(
                                duration = uiEffect.duration,
                                message = uiEffect.message,
                                type = uiEffect.type,
                            )
                        )
                    }
                    is UiEffect.SpinAnimation -> {
                        val vibrationEffect = if (uiState.spinCount < 39)
                            VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
                        else VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)

                        vibrator?.vibrate(vibrationEffect)

                        scale.animateTo(0.5f, animationSpec = tween(500))

                        rotation.animateTo(
                            targetValue = rotation.value + 360f,
                            animationSpec = tween(1000)
                        )

                        scale.animateTo(1f, animationSpec = tween(500))
                        onAction(UiAction.AnimationFinished)
                    }
                }
            }
        }
    }

    TopSnackBar(toasterState)

    Surface {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .scale(scale.value)
                .rotate(rotation.value)
                .background(uiState.backgroundColor),
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
                    .height(60.dp)
                    .shadow(
                        elevation = 24.dp,
                        shape = RoundedCornerShape(percent = 50),
                    ),
                onClick = {
                    onAction(UiAction.OnClickTaklaButton)
                },
                enabled = !uiState.isAnimationPlaying && uiState.spinCount < 40,
            ) {
                Text(
                    text = stringResource(R.string.takla_attir, uiState.spinCount),
                    fontSize = 16.sp
                )
            }
            if (!uiState.isAnimationPlaying && uiState.spinCount == 40) {
                onAction(UiAction.GotoCongratulationScreen)
            }
        }
    }
}
