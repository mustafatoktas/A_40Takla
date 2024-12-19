package com.mustafatoktas.feat_about.composables

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mustafatoktas.feat_about.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTopAppBar(
    pagerState: PagerState,
    onClickNavigationButton: () -> Unit,
    onClickInfoButton: () -> Unit,
) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(context.getString(R.string.kirk_takla_hakkinda))
        },
        navigationIcon = {
            IconButton(
                onClick = onClickNavigationButton
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            if (pagerState.currentPage == 1) {
                IconButton(
                    onClick = onClickInfoButton
                ) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
                }
            }
        }
    )
}