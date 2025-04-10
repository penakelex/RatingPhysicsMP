package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import org.penakelex.rating_physics.R

@Composable
fun DeveloperContact(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(3f),
            text = stringResource(R.string.write_to_developer_label),
            fontSize = 24.sp,
        )

        Image(
            modifier = Modifier
                .weight(1f),
            painter = painterResource(R.drawable.telegram_icon),
            contentDescription = "Telegram icon",
        )
    }
}