package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import org.penakelex.rating_physics.R
import org.penakelex.rating_physics.settings.ThemeState

@Composable
fun ThemeInformation(
    theme: ThemeState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(3f),
            text = stringResource(R.string.theme_label),
            fontSize = 24.sp,
        )

        Icon(
            modifier = Modifier
                .weight(1f),
            painter = painterResource(
                when (theme) {
                    ThemeState.Light -> R.drawable.light_theme_icon
                    ThemeState.Dark -> R.drawable.dark_theme_icon
                    ThemeState.System -> R.drawable.system_theme_icon
                }
            ),
            contentDescription = "Theme icon",
        )
    }
}