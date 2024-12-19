package com.mustafatoktas.feat_about

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.navigation.NavHostController
import com.mustafatoktas.core_common.gecerliOffsetiHesapla
import com.mustafatoktas.feat_about.AboutContract.UiState
import com.mustafatoktas.feat_about.composables.AboutTopAppBar
import com.mustafatoktas.feat_about.composables.DialogLibrariesInfo
import com.mustafatoktas.feat_about.composables.PageInfo
import com.mustafatoktas.feat_about.composables.PageLibaries
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@SuppressLint("RestrictedApi")
@Composable
fun AboutScreen(
    mainNavController: NavHostController,
    uiState: UiState,
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val scope = rememberCoroutineScope()
    var dialogLibrariesInfo by remember { mutableStateOf(false) }

    BackHandler(
        enabled = pagerState.currentPage == 1,
    ) {
        scope.launch {
            pagerState.animateScrollToPage(0)
        }
    }

    Scaffold(
        topBar = {
            AboutTopAppBar(
                pagerState = pagerState,
                onClickNavigationButton = {
                    mainNavController.popBackStack()
                },
                onClickInfoButton = {
                    dialogLibrariesInfo = true
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth()
            ) {
                val tabs = listOf(
                    context.getString(R.string.bilgi),
                    context.getString(R.string.kutuphaneler)
                )

                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->
                val pageOffset = gecerliOffsetiHesapla(page, pagerState)
                val scale = lerp(0.85f, 1f, 1f - pageOffset.absoluteValue)
                val alpha = lerp(0.5f, 1f, 1f - pageOffset.absoluteValue)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    when (page) {
                        0 -> PageInfo(
                            versionNumber = uiState.appVersion,
                            isletimSistemi = uiState.osVersion,
                            telefonModeli = uiState.phoneModel,
                        )
                        1 -> PageLibaries()
                        else -> {}
                    }
                }
            }
        }
    }

    if (dialogLibrariesInfo) {
        DialogLibrariesInfo {
            dialogLibrariesInfo = false
        }
    }
}