package org.penakelex.rating_physics

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "RatingPhysicsMP",
    ) {
        App()
    }
}