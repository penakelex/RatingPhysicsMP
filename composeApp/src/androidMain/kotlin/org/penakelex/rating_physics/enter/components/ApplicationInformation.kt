package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.penakelex.rating_physics.R

@Composable
fun ApplicationInformation(
    applicationIconPainter: Painter,
    version: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = applicationIconPainter,
            contentDescription = "Application image",
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 28.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = version,
                fontSize = 20.sp,
            )
        }
    }
}