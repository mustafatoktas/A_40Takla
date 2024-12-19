package com.mustafatoktas.feat_about.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dokar.sonner.ToastType
import com.dokar.sonner.rememberToasterState
import com.mustafatoktas.core_common.Constants
import com.mustafatoktas.core_ui.composables.TopSnackBar
import com.mustafatoktas.feat_about.R
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun PageInfo(
    versionNumber: String,
    isletimSistemi: String,
    telefonModeli: String,
) {
    val context = LocalContext.current
    val toaster = rememberToasterState()

    val oneri: () -> Unit = {
        val shareText = context.getString(R.string.mustafa_toktasin_gelistirdigi, Constants.GITHUB_LINK)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = Constants.TEXT_PLAIN
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        try {
            context.startActivity(shareIntent)
        } catch (e: Exception) {
            toaster.show(
                message = context.getString(R.string.paylasim_yapilamadi),
                duration = 3300.milliseconds,
                type = ToastType.Error,
            )
        }
    }
    val mail: () -> Unit = {
        val email = Constants.HERO_MAIL
        val subject = context.getString(R.string.app_name_with_version, versionNumber)
        val message = """
                
           
           
           
                ---
                $telefonModeli
                $isletimSistemi
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(Constants.MAIL_TO) // Sadece e-posta uygulamaları tarafından işlenir
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            toaster.show(
                message = context.getString(R.string.eposta_uygulamasi_bulunamadi),
                duration = 3300.milliseconds,
                type = ToastType.Error,
            )
        }
    }

    val bilgiler = listOf(
        Triple(context.getString(R.string.arkadaslariniza_ve_ailenize_onerin), oneri, R.drawable.img_share),
        Triple(context.getString(R.string.iletisime_gecebilirsiniz), mail, R.drawable.img_chat),
    )

    TopSnackBar(toaster)

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(12.dp).padding(top = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            PageInfoHeader(versionNumber = versionNumber,)
        }
        items(bilgiler) { bilgi ->
            InfoRow(
                text = bilgi.first,
                onClick = {
                    bilgi.second.invoke()
                },
                icon = bilgi.third
            )
        }
    }
}
