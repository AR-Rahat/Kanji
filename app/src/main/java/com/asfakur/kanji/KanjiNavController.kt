package com.asfakur.kanji


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.VideoLabel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.asfakur.kanji.screens.home.HomeScreen
import com.asfakur.kanji.screens.home.HomeScreenViewModel
import com.asfakur.kanji.screens.kanji.KanjiViewModel
import com.asfakur.kanji.screens.kanji.details.KanjiDetails
import com.asfakur.kanji.screens.kanjiList.KanjiList
import com.asfakur.kanji.screens.kanjiList.KanjiListViewModel
import com.asfakur.kanji.screens.splash.SplashScreen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object KanjiList : Screen("kanjiList")
    data object KanjiDetails : Screen("kanji")
}

sealed class SplashScreen(val route: String) {
    data object Splash : SplashScreen("splash/index")
}

sealed class KanjiListScreen(val route: String) {
    data object KanjiList : KanjiListScreen("kanjiList/index/{week}") {
        const val WEEK = "week"
    }
}

sealed class HomeScreen(val route: String) {
    data object Home : HomeScreen("home/index")
}

sealed class KanjiScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object KanjiDetails : KanjiScreen(
        route = "kanji/details/{week}/{Id}",
        title = "Details",
        icon = Icons.Default.Details
    ) {
        const val WEEK = "week"
        const val ID = "Id"
    }

    data object KanjiMedia : KanjiScreen(
        route = "kanji/media/{week}/{Id}",
        title = "Media",
        icon = Icons.Default.OndemandVideo
    ){
        const val WEEK = "week"
        const val ID = "Id"
    }

    data object KanjiPdf : KanjiScreen(
        route = "kanji/pdf/{week}/{Id}",
        title = "Pdf",
        icon = Icons.Default.DocumentScanner
    ){
        const val WEEK = "week"
        const val ID = "Id"
    }

    data object KanjiTutorial : KanjiScreen(
        route = "kanji/tutorial/{week}/{Id}",
        title = "Tutorial",
        icon = Icons.Default.VideoLabel
    ){
        const val WEEK = "week"
        const val ID = "Id"
    }
}


@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        addSplashScreen(navController = navController)
        addHomeScreen(navController = navController)
        addKanjiListScreen(navController = navController)
        addKanjiScreen(navController = navController)
    }
}

private fun NavGraphBuilder.addSplashScreen(
    navController: NavHostController
) {
    navigation(route = Screen.Splash.route, startDestination = SplashScreen.Splash.route) {
        composable(SplashScreen.Splash.route) {
            SplashScreen(gotoHomeIndex = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(SplashScreen.Splash.route) {
                        inclusive = true
                    }
                }
            })
        }
    }
}

private fun NavGraphBuilder.addHomeScreen(
    navController: NavHostController
) {
    navigation(route = Screen.Home.route, startDestination = HomeScreen.Home.route) {
        composable(HomeScreen.Home.route) {
            val viewModel = it.sharedViewModel<HomeScreenViewModel>(navController = navController)
            HomeScreen(viewModel, gotoKanjiListIndex = { week ->
                navController.navigate(
                    KanjiListScreen.KanjiList.route.replaceFirst(
                        "{${KanjiListScreen.KanjiList.WEEK}}", week
                    )
                )
            })
        }
    }
}

private fun NavGraphBuilder.addKanjiListScreen(
    navController: NavHostController
) {
    navigation(route = Screen.KanjiList.route, startDestination = KanjiListScreen.KanjiList.route) {
        composable(
            KanjiListScreen.KanjiList.route,
            arguments = listOf(navArgument(KanjiListScreen.KanjiList.WEEK) {
                type = NavType.StringType
            })
        ) {
            val viewModel = it.sharedViewModel<KanjiListViewModel>(navController = navController)
            KanjiList(viewModel,
                Week = it.arguments?.getString(KanjiListScreen.KanjiList.WEEK) ?: "Week 1",
                gotoKanjiDetailsIndex = { week, id ->
                    //println(kanji)
                    navController.navigate(
                        KanjiScreen.KanjiDetails.route
                            .replaceFirst("{${KanjiScreen.KanjiDetails.WEEK}}", newValue = week)
                            .replaceFirst("{${KanjiScreen.KanjiDetails.ID}}", newValue = id)
                    )
                },
                goBack = {
                    navController.popBackStack()
                })
        }
    }
}

private fun NavGraphBuilder.addKanjiScreen(
    navController: NavHostController
) {
    navigation(
        route = Screen.KanjiDetails.route,
        startDestination = KanjiScreen.KanjiDetails.route
    ) {
        composable(
            KanjiScreen.KanjiDetails.route,
            arguments = listOf(
                navArgument(KanjiScreen.KanjiDetails.WEEK) { type = NavType.StringType },
                navArgument(KanjiScreen.KanjiDetails.ID) { type = NavType.StringType },)
        ) {
            val viewModel = it.sharedViewModel<KanjiViewModel>(navController = navController)
            KanjiDetails(
                viewModel = viewModel,
                week = it.arguments?.getString(KanjiScreen.KanjiDetails.WEEK) ?: "Week 1",
                id = it.arguments?.getString(KanjiScreen.KanjiDetails.ID) ?: "A1",
                navController = navController,
                goBack = {
                    navController.popBackStack(KanjiListScreen.KanjiList.route, inclusive = false)
                }
            )
        }
        composable(KanjiScreen.KanjiMedia.route,
            arguments = listOf(
                navArgument(KanjiScreen.KanjiMedia.WEEK) { type = NavType.StringType },
                navArgument(KanjiScreen.KanjiMedia.ID) { type = NavType.StringType },
            )){
            val viewModel = it.sharedViewModel<KanjiViewModel>(navController = navController)
            KanjiDetails(
                viewModel = viewModel,
                week = it.arguments?.getString(KanjiScreen.KanjiMedia.WEEK) ?: "Week 1",
                id = it.arguments?.getString(KanjiScreen.KanjiMedia.ID) ?: "A1",
                navController = navController,
                goBack = {
                    navController.popBackStack(KanjiListScreen.KanjiList.route, inclusive = false)
                }
            )
        }
        composable(
            KanjiScreen.KanjiPdf.route,
            arguments = listOf(
                navArgument(KanjiScreen.KanjiPdf.WEEK) { type = NavType.StringType },
                navArgument(KanjiScreen.KanjiPdf.ID) { type = NavType.StringType }
            )
        ) {
            val viewModel = it.sharedViewModel<KanjiViewModel>(navController = navController)
            KanjiDetails(
                viewModel = viewModel,
                week = it.arguments?.getString(KanjiScreen.KanjiPdf.WEEK) ?: "Week 1",
                id = it.arguments?.getString(KanjiScreen.KanjiPdf.ID) ?: "A1",
                navController = navController,
                goBack = {
                    navController.popBackStack(KanjiListScreen.KanjiList.route, inclusive = false)
                }
            )
        }
        composable(
            KanjiScreen.KanjiTutorial.route,
            arguments = listOf(
                navArgument(KanjiScreen.KanjiTutorial.WEEK) { type = NavType.StringType },
                navArgument(KanjiScreen.KanjiTutorial.ID) { type = NavType.StringType }
            )
        ) {
            val viewModel = it.sharedViewModel<KanjiViewModel>(navController = navController)
            KanjiDetails(
                viewModel = viewModel,
                week = it.arguments?.getString(KanjiScreen.KanjiTutorial.WEEK) ?: "Week 1",
                id = it.arguments?.getString(KanjiScreen.KanjiTutorial.ID) ?: "A1",
                navController = navController,
                goBack = {
                    navController.popBackStack(KanjiListScreen.KanjiList.route, inclusive = false)
                }
            )
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}


