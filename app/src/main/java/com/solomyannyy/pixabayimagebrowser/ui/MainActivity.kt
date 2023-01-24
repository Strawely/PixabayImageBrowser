package com.solomyannyy.pixabayimagebrowser.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.solomyannyy.pixabayimagebrowser.details.ui.DetailsScreen
import com.solomyannyy.pixabayimagebrowser.details.viewmodel.DetailsViewModel
import com.solomyannyy.pixabayimagebrowser.main.ui.MainScreen
import com.solomyannyy.pixabayimagebrowser.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(navController, startDestination = NAV_DESTINATION_MAIN) {
                    mainScreenDestination(navController)
                    detailsScreenDestination()
                }
            }
        }
    }

    private fun NavGraphBuilder.mainScreenDestination(navController: NavController) {
        composable(NAV_DESTINATION_MAIN) {
            val viewModel: MainViewModel = hiltViewModel()
            MainScreen(viewModel) { navController.navigate("$NAV_DESTINATION_DETAILS/${it}") }
        }
    }

    private fun NavGraphBuilder.detailsScreenDestination() {
        composable(
            route = "$NAV_DESTINATION_DETAILS/{$NAV_ARG_IMAGE_ID}",
            arguments = listOf(navArgument(NAV_ARG_IMAGE_ID) { type = NavType.LongType })
        ) {
            val imageId = it.arguments?.getLong(NAV_ARG_IMAGE_ID, -1) ?: -1
            val viewModel: DetailsViewModel = hiltViewModel<DetailsViewModel>().apply {
                updateContent(imageId)
            }
            DetailsScreen(imageId, viewModel)
        }

    }

    private companion object {
        const val NAV_ARG_IMAGE_ID = "image_id"
        const val NAV_DESTINATION_MAIN = "main"
        const val NAV_DESTINATION_DETAILS = "details"
    }
}
