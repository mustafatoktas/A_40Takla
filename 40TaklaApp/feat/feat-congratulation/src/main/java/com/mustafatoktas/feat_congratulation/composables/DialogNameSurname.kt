package com.mustafatoktas.feat_congratulation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mustafatoktas.feat_congratulation.R

@Composable
fun DialogNameSurname(
    onDismissRequest: () -> Unit,
    onClickButton: () -> Unit,
    text: String,
    onValueChange: (String) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = onValueChange,
                    label = { Text(stringResource(R.string.isim_soyisim)) },
                    modifier = Modifier.padding(bottom = 16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words
                    ),
                )
                Button(
                    onClick = onClickButton,
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    enabled = text.isNotBlank()
                ) {
                    Text(stringResource(R.string.taklalari_olumsuzlestir))
                }
            }
        }
    }
}
