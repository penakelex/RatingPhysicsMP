package org.penakelex.rating_physics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.compose.KoinContext
import org.penakelex.rating_physics.enter.EnterScreen
import org.penakelex.rating_physics.rating.RatingDataScreen
import org.penakelex.rating_physics.ui.theme.RatingPhysicsTheme
import org.penakelex.rating_physics.util.Screen
import kotlin.text.format

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RatingPhysicsTheme {
                KoinContext {
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = Screen.EnterScreen.route,
                        ) {
                            composable(route = Screen.EnterScreen.route) {
                                EnterScreen(navController)
                            }

                            composable(
                                route = "%s?password={password}&filePath={filePath}"
                                    .format(Screen.RatingDataScreen.route),
                                arguments = listOf(
                                    navArgument(name = "password") {
                                        type = NavType.Companion.IntType
                                    },
                                    navArgument(name = "filePath") {
                                        type = NavType.Companion.StringType
                                    },
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
}