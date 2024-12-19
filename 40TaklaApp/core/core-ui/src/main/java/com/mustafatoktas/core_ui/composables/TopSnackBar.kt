package com.mustafatoktas.core_ui.composables

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterState

@Composable
fun TopSnackBar(toasterState: ToasterState) {
    Toaster(
        state = toasterState,
        alignment = Alignment.TopCenter,
        modifier = Modifier.navigationBarsPadding().systemBarsPadding()
    )
}
