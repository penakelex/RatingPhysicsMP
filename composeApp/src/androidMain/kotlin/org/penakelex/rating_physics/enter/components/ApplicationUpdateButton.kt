package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import org.penakelex.rating_physics.R

@Composable
fun ApplicationUpdateButton(
    currentVersion: String,
    latestVersion: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.update_label),
            fontSize = 24.sp,
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "v$currentVersion -> v$latestVersion",
            fontSize = 12.sp,
        )
    }
}