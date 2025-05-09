package org.penakelex.rating_physics.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import java.awt.Desktop
import java.net.URI

const val CURRENT_APPLICATION_VERSION = "1.0.0"
const val APPLICATION_UPDATE_URL = ""

@Composable
fun rememberApplicationInformationStateHolder(): ApplicationInformationStateHolder {
    val stateHolder = koinInject<ApplicationInformationStateHolder>()

    val state = remember {
        stateHolder
    }

    DisposableEffect(Unit) {
        onDispose {
            state.cancel()
        }
    }

    return state
}

@Composable
fun ApplicationInformation(modifier: Modifier = Modifier) {
    val state = rememberApplicationInformationStateHolder()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        state.latestVersion.value?.let {
            if (it != CURRENT_APPLICATION_VERSION) {
                Row(
                    modifier = Modifier
                        .clickable {
                            if (!Desktop.isDesktopSupported())
                                return@clickable

                            val desktop = Desktop.getDesktop()

                            if (!desktop.isSupported(Desktop.Action.BROWSE))
                                return@clickable

                            try {
                                desktop.browse(URI(APPLICATION_UPDATE_URL))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Обновить:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = "v$CURRENT_APPLICATION_VERSION -> v$it",
                        fontSize = 10.sp,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = buildAnnotatedString {
                append(
                    AnnotatedString(
                        text = "Разработчик: ",
                        spanStyle = SpanStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                )
                append(
                    AnnotatedString(
                        text = "Конинский Алексей",
                        spanStyle = SpanStyle(
                            fontStyle = FontStyle.Italic,
                        )
                    )
                )
            },
            fontSize = 10.sp,
        )
    }
}