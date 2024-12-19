package com.mustafatoktas.kirktakla

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.mustafatoktas.core_ui.theme.KirkTaklaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            val mainNavController = rememberNavController()
            KirkTaklaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    KirkTaklaNavHost(mainNavController)
                }
            }
        }
    }
}
