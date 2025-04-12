package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FileInputField(
    filePath: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier,
        value = filePath,
        onValueChange = onValueChange,
        trailingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
                contentDescription = "Folder icon",
            )
        },
        label = {
            Text(
                text = "Файл",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        placeholder = {
            Text(
                text = "Путь к файлу",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = RoundedCornerShape(10.dp),
        isError = isError,
        singleLine = true,
    )
}