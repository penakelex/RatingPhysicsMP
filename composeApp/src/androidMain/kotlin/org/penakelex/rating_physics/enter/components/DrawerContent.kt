package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.penakelex.rating_physics.settings.ThemeState

@Composable
fun DrawerContent(
    currentVersion: String,
    latestVersion: String?,
    applicationIconPainter: Painter,
    theme: ThemeState,
    isThemeSelectionDialogOpened: MutableState<Boolean>,
    onDeveloperContactClick: () -> Unit,
    onApplicationUpdateClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(IntrinsicSize.Min),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            ApplicationInformation(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(),
                applicationIconPainter = applicationIconPainter,
                version = currentVersion
            )

            Spacer(modifier = Modifier.height(24.dp))

            DeveloperContact(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onDeveloperContactClick),
            )

            Spacer(modifier = Modifier.height(18.dp))

            ThemeInformation(
                modifier = Modifier
                    .clickable {
                        isThemeSelectionDialogOpened.value = true
                    },
                theme = theme,
            )

            if (latestVersion != null && latestVersion != currentVersion) {
                Spacer(modifier = Modifier.height(18.dp))

                ApplicationUpdateButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onApplicationUpdateClick),
                    currentVersion = currentVersion,
                    latestVersion = latestVersion,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            DeveloperInformation()
        }
    }
}