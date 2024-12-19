package com.mustafatoktas.feat_about.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mustafatoktas.feat_about.R

@Composable
fun DialogLibrariesInfo(
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = context.getString(R.string.kullanılan_kutuphaneler_baslik))
        },
        text = {
            Text(text = context.getString(R.string.kullanılan_kutuphaneler_icerik))
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = context.getString(R.string.tamam))
            }
        },
    )
}
