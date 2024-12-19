package com.mustafatoktas.feat_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.dokar.sonner.Toast
import com.dokar.sonner.rememberToasterState
import com.google.firebase.Timestamp
import com.mustafatoktas.core_ui.composables.TopSnackBar
import com.mustafatoktas.feat_list.ListContract.UiAction
import com.mustafatoktas.feat_list.ListContract.UiEffect
import com.mustafatoktas.feat_list.ListContract.UiState
import com.mustafatoktas.feat_list.composables.ErrorScreen
import com.mustafatoktas.feat_list.composables.LazyColumnHeader
import com.mustafatoktas.feat_list.composables.ListTopAppBar
import com.mustafatoktas.feat_list.composables.UserRow
import dev.materii.pullrefresh.DragRefreshIndicator
import dev.materii.pullrefresh.DragRefreshLayout
import dev.materii.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    mainNavController: NavHostController,
    uiEffect: Flow<UiEffect>
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val toasterState = rememberToasterState()

    LaunchedEffect(uiEffect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiEffect.collect { uiEffect ->
                when (uiEffect) {
                    is UiEffect.ShowSnackBar -> {
                        toasterState.show(
                            Toast(
                                message = uiEffect.message,
                                type = uiEffect.type,
                            )
                        )
                    }
                }
            }
        }
    }

    Box {
        TopSnackBar(toasterState)

        when {
            uiState.isLoading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.usersList.isNotEmpty() -> {
                ListContent(
                    uiState = uiState,
                    onAction = onAction,
                    mainNavController = mainNavController,
                )
            }
            else -> {
                ErrorScreen {
                    onAction(UiAction.OnClickRefreshButton)
                }
            }
        }
    }
}


@Composable
fun ListContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    mainNavController: NavHostController,
) {
    val scope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = {
            scope.launch {
                onAction(UiAction.OnRefresh)
            }
        }
    )

    Scaffold(
        topBar = {
            ListTopAppBar(
                title = stringResource(R.string.takla_attiranlar),
                onClickNavigationIcon = {
                    mainNavController.navigateUp()
                }
            )
        },
    ) { padding ->

        DragRefreshLayout(
            modifier = Modifier.fillMaxSize().padding(padding),
            state = pullRefreshState,
            indicator = {
                DragRefreshIndicator(
                    state = pullRefreshState,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    LazyColumnHeader(uiState.usersList.size)
                }
                items(uiState.usersList.size) { index ->
                    UserRow(
                        nameSurname = uiState.usersList[index].isimSoyisim ?: String(),
                        timeStamp = uiState.usersList[index].timeStamp ?: Timestamp(946684800, 0), //tarihin 01/01/2000 olarak gösterilmesi için
                        isCurrentUserID = uiState.usersList[index].userID == uiState.currentUserID,
                    ) {
                        onAction(UiAction.OnClickUserRowItem)
                    }
                }
            }
        }
    }
}
