package com.mustafatoktas.feat_main

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokar.sonner.ToastType
import com.mustafatoktas.core_common.DeviceStatus
import com.mustafatoktas.core_common.Resource
import com.mustafatoktas.usecase_app.AppUseCases
import com.mustafatoktas.usecase_firebase.FirebaseUseCases
import com.mustafatoktas.feat_main.MainContract.UiAction
import com.mustafatoktas.feat_main.MainContract.UiEffect
import com.mustafatoktas.feat_main.MainContract.UiState
import com.mustafatoktas.core_ui.theme.renk1
import com.mustafatoktas.core_ui.theme.renk2
import com.mustafatoktas.core_ui.theme.renk3
import com.mustafatoktas.core_ui.theme.renk4
import com.mustafatoktas.core_ui.theme.renk5
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val firebaseUseCases: FirebaseUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    private suspend fun emitUiEffect(uiEffect: UiEffect) = _uiEffect.send(uiEffect)
    private fun updateUiState(block: UiState.() -> UiState) = _uiState.update(block)

    init {
        getDeviceState()
        firebaseSignIn()
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnClickTaklaButton -> onClickTaklaButton()
            is UiAction.GotoCongratulationScreen -> viewModelScope.launch {
                emitUiEffect(UiEffect.GoToCongratulationScreen)
            }

            is UiAction.AnimationFinished -> updateUiState { copy(isAnimationPlaying = false) }
        }
    }

    private fun firebaseSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!firebaseUseCases.isUserLoggedIn.invoke()) {
                firebaseUseCases.signInAnonymously()
            } else {
                val currentUserID = firebaseUseCases.getCurrentUserID.invoke()
                getNameSurname(currentUserID)
            }
        }
    }

    private fun getNameSurname(currentUserID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (firebaseUseCases.getUserNameSurnameByID(currentUserID)) {
                is Resource.Success -> {
                    emitUiEffect(UiEffect.ShowSnackBar(
                        message = appUseCases.getString(R.string.daha_once_isim_kaydedilmis),
                        type = ToastType.Success,
                        duration = 6_700.milliseconds
                    ))
                    updateUiState { copy(spinCount = 38) }
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun onClickTaklaButton() {
        viewModelScope.launch {
            updateUiState { copy(spinCount = spinCount + 1, isAnimationPlaying = true) }
            updateBackgroundColor()
            emitUiEffect(UiEffect.SpinAnimation)
            taklaMotivasyonu(uiState.value.spinCount)
        }
    }

    private fun taklaMotivasyonu(spinCount: Int) {
        viewModelScope.launch {
            when (spinCount) {
                1 -> showSnackBar(appUseCases.getString(R.string.baslamak_bitirmenin_yarisidir))
                3 -> showSnackBar(appUseCases.getString(R.string.iyi_gidiyorsun))
                5 -> showSnackBar(appUseCases.getString(R.string.bes_takla_bitti_bile))
                10 -> showSnackBar(appUseCases.getString(R.string.yuzde_25_i_tamamlandi))
                15 -> showSnackBar(appUseCases.getString(R.string.pes_etmek_yok_devam))
                20 -> showSnackBar(appUseCases.getString(R.string.yolu_yariladin))
                25 -> showSnackBar(appUseCases.getString(R.string.sadece_15_takla_kaldi))
                30 -> showSnackBar(appUseCases.getString(R.string.yuzde_75_tamamlandi))
                35 -> showSnackBar(appUseCases.getString(R.string.bitirmek_icin_sadece_5_takla_daha))
                39 -> showSnackBar(appUseCases.getString(R.string.son_1_takla_daha))
            }
        }
    }

    private fun showSnackBar(
        message: String,
        type: ToastType = ToastType.Success,
        duration: Duration = 2_700.milliseconds,
    ) {
        viewModelScope.launch {
            emitUiEffect(UiEffect.ShowSnackBar(message = message, type = type, duration = duration))
        }
    }

    private fun updateBackgroundColor() {
        val backgroundColors = listOf(
            renk1,
            renk2,
            renk3,
            renk4,
            renk5,
        )
        val nextIndex = (uiState.value.backgroundColorIndex + 1) % backgroundColors.size
        val nextColor = backgroundColors[nextIndex]
        updateUiState {
            copy(
                backgroundColorIndex = nextIndex,
                backgroundColor = nextColor
            )
        }
    }

    private fun getDeviceState() {
        viewModelScope.launch {
            val isRootedDeferred = async { isRooted() }
            val isEmulatorDeferred = async { isEmulator() }

            val isRooted = isRootedDeferred.await()
            val isEmulator = isEmulatorDeferred.await()

            updateUiState {
                copy(
                    deviceStatus = when {
                        isRooted -> DeviceStatus.Rooted
                        isEmulator -> DeviceStatus.Emulator
                        else -> DeviceStatus.Normal
                    }
                )
            }
        }
    }

    private suspend fun isRooted(): Boolean {
        return isRootedMethod1() || isRootedMethod2()
    }

    private suspend fun isRootedMethod1(): Boolean {
        return withContext(Dispatchers.IO) {
            val superuserApk = File("/system/app/Superuser.apk")
            val suBinary = File("/system/bin/su")
            superuserApk.exists() || suBinary.exists()
        }
    }

    private suspend fun isRootedMethod2(): Boolean {
        return withContext(Dispatchers.IO) {
            var process: Process? = null
            return@withContext try {
                process = Runtime.getRuntime().exec("su")
                true
            } catch (e: Exception) {
                false
            } finally {
                process?.destroy()
            }
        }
    }

    private suspend fun isEmulator(): Boolean {
        return withContext(Dispatchers.IO) {
            (
                    Build.FINGERPRINT.startsWith("google/sdk_gphone_") &&
                            Build.FINGERPRINT.endsWith(":user/release-keys") &&
                            Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone") && Build.BRAND == "google" &&
                            Build.MODEL.startsWith("sdk_gphone")
                    ) ||
                    Build.FINGERPRINT.startsWith("generic") ||
                    Build.FINGERPRINT.startsWith("unknown") ||
                    Build.HARDWARE.contains("goldfish") ||
                    Build.HARDWARE.contains("ranchu") ||
                    Build.MODEL.contains("google_sdk") ||
                    Build.MODEL.contains("Emulator") ||
                    Build.MODEL.contains("Android SDK built for x86") ||
                    Build.MANUFACTURER.contains("Genymotion") ||
                    Build.HOST == "Build2" || // MSI App Player
                    Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
                    Build.PRODUCT.contains("sdk_google") ||
                    Build.PRODUCT == "google_sdk" ||
                    Build.PRODUCT.contains("sdk") ||
                    Build.PRODUCT.contains("sdk_x86") ||
                    Build.PRODUCT.contains("vbox86p") ||
                    Build.PRODUCT.contains("emulator") ||
                    Build.PRODUCT.contains("simulator")
        }
    }
}
