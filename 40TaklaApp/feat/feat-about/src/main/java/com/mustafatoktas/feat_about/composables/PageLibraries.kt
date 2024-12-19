package com.mustafatoktas.feat_about.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mustafatoktas.core_common.ConstantsLibrary
import com.mustafatoktas.core_common.openToBrowser

@Composable
fun PageLibaries() {
    val context = LocalContext.current
    val libraries = listOf(
        Triple(ConstantsLibrary.SONNER, ConstantsLibrary.SONNER_LICENSE, ConstantsLibrary.SONNER_URL),
        Triple(ConstantsLibrary.COIL, ConstantsLibrary.COIL_LICENSE, ConstantsLibrary.COIL_URL),
        Triple(ConstantsLibrary.KONFETTI, ConstantsLibrary.KONFETTI_LICENSE, ConstantsLibrary.KONFETTI_URL),
        Triple(ConstantsLibrary.PULL_TO_REFRESH, ConstantsLibrary.PULL_TO_REFRESH_LICENSE, ConstantsLibrary.PULL_TO_REFRESH_URL),
        Triple(ConstantsLibrary.DETEKT, ConstantsLibrary.DETEKT_LICENSE, ConstantsLibrary.DETEKT_URL),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 6.dp)
            .padding(top = 4.dp),
    ) {
        items(libraries) { library ->
            LibraryRow(
                title = library.first,
                description = library.second,
            ) {
                context.openToBrowser(library.third)
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
