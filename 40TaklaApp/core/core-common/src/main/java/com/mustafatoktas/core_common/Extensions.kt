package com.mustafatoktas.core_common

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mustafatoktas.core_ui.Destination

fun NavHostController.navigateAndClearBackStack(destination: Destination) {
    this.navigate(destination) {
        popUpTo(this@navigateAndClearBackStack.graph.findStartDestination().id) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateAndSaveState(destination: Destination) {
    this.navigate(destination) {
        popUpTo(this@navigateAndSaveState.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun Context.openToBrowser(url: String?) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url ?: String()))
    startActivity(browserIntent)
}
