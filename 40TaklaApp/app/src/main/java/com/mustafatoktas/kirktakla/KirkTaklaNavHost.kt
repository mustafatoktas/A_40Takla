package com.mustafatoktas.kirktakla

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mustafatoktas.core_common.mySlideInHorizontalyNegatif
import com.mustafatoktas.core_common.mySlideInHorizontalyPozitif
import com.mustafatoktas.core_common.mySlideInVerticallyNegatif
import com.mustafatoktas.core_common.mySlideInVerticallyPozitif
import com.mustafatoktas.core_common.mySlideOutHorizontalyNegatif
import com.mustafatoktas.core_common.mySlideOutHorizontalyPozitif
import com.mustafatoktas.core_common.mySlideOutVerticallyNegatif
import com.mustafatoktas.core_common.mySlideOutVerticallyPozitif
import com.mustafatoktas.core_ui.Destination
import com.mustafatoktas.core_ui.Graph
import com.mustafatoktas.feat_about.AboutScreen
import com.mustafatoktas.feat_about.AboutViewModel
import com.mustafatoktas.feat_congratulation.CongratulationScreen
import com.mustafatoktas.feat_congratulation.CongratulationViewModel
import com.mustafatoktas.feat_list.ListScreen
import com.mustafatoktas.feat_list.ListViewModel
import com.mustafatoktas.feat_main.MainScreen
import com.mustafatoktas.feat_main.MainViewModel

@Composable
fun KirkTaklaNavHost(
    mainNavController: NavHostController,
) {
    NavHost(
        navController = mainNavController,
        startDestination = Graph.MainGraph,
    ) {
        navigation<Graph.MainGraph> (
            startDestination = Destination.MainScreen,
        ) {
            composable<Destination.MainScreen> {
                val viewModel: MainViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val uiEffect = viewModel.uiEffect
                MainScreen(
                    uiState = uiState,
                    mainNavController = mainNavController,
                    uiEffect = uiEffect,
                    onAction = viewModel::onAction
                )
            }
            composable<Destination.CongratulationScreen>(
                enterTransition = { mySlideInHorizontalyPozitif() },
                exitTransition = { mySlideOutHorizontalyNegatif() },
                popEnterTransition = { mySlideInHorizontalyNegatif() },
                popExitTransition = { mySlideOutHorizontalyPozitif() },
            ) {
                val viewModel: CongratulationViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val uiEffect = viewModel.uiEffect
                CongratulationScreen(
                    uiState = uiState,
                    uiEffect = uiEffect,
                    onAction = viewModel::onAction,
                    mainNavController = mainNavController,
                )
            }
            composable<Destination.ListScreen>(
                enterTransition = { mySlideInHorizontalyPozitif() },
                exitTransition = { mySlideOutHorizontalyNegatif() },
                popEnterTransition = { mySlideInHorizontalyNegatif() },
                popExitTransition = { mySlideOutHorizontalyPozitif() },
            ) {
                val viewModel: ListViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val uiEffect = viewModel.uiEffect
                ListScreen(
                    uiState = uiState,
                    onAction = viewModel::onAction,
                    uiEffect = uiEffect,
                    mainNavController = mainNavController,
                )
            }
            composable<Destination.AboutScreen>(
                enterTransition = { mySlideInVerticallyPozitif() },
                exitTransition = { mySlideOutVerticallyNegatif() },
                popEnterTransition = { mySlideInVerticallyNegatif() },
                popExitTransition = { mySlideOutVerticallyPozitif() },
            ) {
                val viewModel: AboutViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                AboutScreen(
                    mainNavController = mainNavController,
                    uiState = uiState,
                )
            }
        }
    }
}
