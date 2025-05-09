package org.penakelex.rating_physics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext
import org.penakelex.rating_physics.enter.EnterScreen
import org.penakelex.rating_physics.rating.RatingDataScreen
import org.penakelex.rating_physics.settings.SettingsViewModel
import org.penakelex.rating_physics.settings.isDarkTheme
import org.penakelex.rating_physics.ui.theme.RatingPhysicsTheme
import org.penakelex.rating_physics.util.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsViewModel by viewModel<SettingsViewModel>()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
               !settingsViewModel.isInitialDataLoaded()
            }
        }

        setContent {
            KoinContext {
                val theme by settingsViewModel.themeState

                RatingPhysicsTheme(theme.isDarkTheme(isSystemInDarkTheme())) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.EnterScreen.route,
                    ) {
                        composable(route = Screen.EnterScreen.route) {
                            EnterScreen(navController, settingsViewModel)
                        }

                        composable(
                            route = "%s?password={password}&path={path}&type={type}"
                                .format(Screen.RatingDataScreen.route),
                            arguments = listOf(
                                navArgument(name = "password") {
                                    type = NavType.IntType
                                },
                                navArgument(name = "path") {
                                    type = NavType.StringType
                                },
                                navArgument(name = "type") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            RatingDataScreen(navController)
                        }
                    }
                }
            }
        }
    }
}