package org.penakelex.rating_physics

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import org.koin.compose.KoinApplication
import org.penakelex.rating_physics.di.appModule
import org.penakelex.rating_physics.di.dataModule
import org.penakelex.rating_physics.di.domainModule
import org.penakelex.rating_physics.enter.EnterView
import org.penakelex.rating_physics.information.ApplicationInformation
import org.penakelex.rating_physics.rating.RatingEvent
import org.penakelex.rating_physics.rating.RatingView
import org.penakelex.rating_physics.rating.rememberRatingStateHolder
import org.penakelex.rating_physics.ui.theme.RatingPhysicsTheme

fun main() = singleWindowApplication(
    title = "RatingPhysics",
    state = WindowState(
        placement = WindowPlacement.Floating,
    ),
) {
    KoinApplication(
        application =  {
            modules(appModule, domainModule, dataModule)
        }
    ) {
        val focusManager = LocalFocusManager.current

        val ratingStateHolder = rememberRatingStateHolder()

        RatingPhysicsTheme(isDarkTheme = false) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    focusManager.clearFocus()
                                }
                            )
                        },
                ) {
                    EnterView(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onDataValidateCallback = { password, filePath ->
                            ratingStateHolder.onEvent(
                                RatingEvent.RequestedDataSearch(password, filePath)
                            )
                        },
                    )

                    Spacer(Modifier.height(18.dp))

                    RatingView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        stateHolder = ratingStateHolder,
                    )

                    Spacer(Modifier.height(4.dp))

                    ApplicationInformation(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}