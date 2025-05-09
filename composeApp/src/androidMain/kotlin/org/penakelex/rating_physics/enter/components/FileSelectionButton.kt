package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.penakelex.rating_physics.R

@Composable
fun FileSelectionButton(
    modifier: Modifier = Modifier,
    fileName: String?,
    isFileValid: Boolean,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .size(48.dp),
            imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
            contentDescription = "File",
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = fileName ?: stringResource(R.string.file_not_chosen_hint),
            fontSize = 24.sp,
            color = if (isFileValid) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.error,
        )
    }
}