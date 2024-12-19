package com.mustafatoktas.feat_congratulation

import android.app.Activity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dokar.sonner.TextToastAction
import com.dokar.sonner.Toast
import com.dokar.sonner.ToastType
import com.dokar.sonner.rememberToasterState
import com.mustafatoktas.core_common.makeKonfeti
import com.mustafatoktas.core_common.navigateAndSaveState
import com.mustafatoktas.feat_congratulation.CongratulationContract.UiAction
import com.mustafatoktas.feat_congratulation.CongratulationContract.UiEffect
import com.mustafatoktas.feat_congratulation.CongratulationContract.UiState
import com.mustafatoktas.core_ui.Destination
import com.mustafatoktas.core_ui.composables.TopSnackBar
import com.mustafatoktas.feat_congratulation.composables.DialogNameSurname
import dev.materii.pullrefresh.DragRefreshIndicator
import dev.materii.pullrefresh.DragRefreshLayout
import dev.materii.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import nl.dionsegijn.konfetti.compose.KonfettiView
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CongratulationScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    mainNavController: NavHostController,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val toasterState = rememberToasterState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = {
            scope.launch {
                onAction(UiAction.ChangeRefreshState(true))
                onAction(UiAction.OnRefresh)
                onAction(UiAction.ChangeRefreshState(false))
            }
        }
    )

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { uiEffect ->
                when (uiEffect) {
                    is UiEffect.RestartApp -> {
                        val activity = (context as? Activity)
                        toasterState.show(
                            Toast(
                                duration = 1_500.milliseconds,
                                message = context.getString(R.string.yeniden_baslatiliyor),
                                type = ToastType.Info
                            )
                        )
                        delay(1_500)
                        activity?.let {
                            it.finish()
                            it.startActivity(it.intent)
                        }
                    }
                    is UiEffect.ShowSnackBar -> {
                        toasterState.show(
                            Toast(
                                duration = uiEffect.duration,
                                message = uiEffect.message,
                                type = uiEffect.type,
                                action = if (uiEffect.isHaveButton) TextToastAction(
                                    text = context.getString(R.string.yeniden_dene),
                                    onClick = {
                                        toasterState.dismiss(it)
                                        onAction(UiAction.SetNameSurnameToFirebase)
                                    },
                                ) else null,
                            )
                        )
                    }
                    is UiEffect.NavigateScreen -> {
                        when (uiEffect.destination) {
                            is Destination.ListScreen -> mainNavController.navigateAndSaveState(Destination.ListScreen)
                            is Destination.AboutScreen -> mainNavController.navigateAndSaveState(Destination.AboutScreen)
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    TopSnackBar(toasterState)

    DragRefreshLayout(
        modifier = Modifier.fillMaxSize(),
        state = pullRefreshState,
        indicator = {
            DragRefreshIndicator(
                state = pullRefreshState,
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = stringResource(R.string.tebrikler),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 24.dp,top = 60.dp)
            )
            AsyncImage(
                model = R.drawable.img_unemployment,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onAction(UiAction.OnClickNavigate(Destination.AboutScreen))
                    }
                    .size(300.dp)
                    .padding()
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    ),
            )
            Text(
                text = stringResource(R.string.tebrikler_icerigi),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 18.dp, top = 36.dp)
            )

            Button(
                enabled = uiState.isListeyeKatilButtonEnabled,
                modifier = Modifier
                    .padding(bottom = 18.dp),
                onClick = {
                    onAction(UiAction.ChangeDialogVisibility)
                }
            ) {
                Text(text = stringResource(R.string.kirk_takla_attiranlar_listesine_katil))
            }
            OutlinedButton(
                onClick = {
                    onAction(UiAction.OnClickNavigate(Destination.ListScreen))
                }
            ) {
                Text(text = stringResource(R.string.listeyi_gor))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (uiState.isDialogVisible)
        DialogNameSurname(
            text = uiState.nameSurname,
            onValueChange = {
                onAction(UiAction.ChangeNameSurname(it))
            },
            onDismissRequest = {
                onAction(UiAction.ChangeDialogVisibility)
                onAction(UiAction.ChangeNameSurname(String()))
            },
            onClickButton = {
                onAction(UiAction.ChangeDialogVisibility)
                onAction(UiAction.SetNameSurnameToFirebase)
            },
        )

    if (!uiState.isGoToListScreen)
        KonfettiView(parties = makeKonfeti())

}
