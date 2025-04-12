package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PasswordInputField(
    password: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier,
        value = password,
        onValueChange = onValueChange,
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Password,
                contentDescription = "Password icon"
            )
        },
        label = {
            Text(
                text = "Пароль",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        placeholder = {
            Text(
                text = "№ в группе + № группы",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
            )
        },
        shape = RoundedCornerShape(10.dp),
        textStyle = MaterialTheme.typography.bodyLarge,
        isError = isError,
        singleLine = true,
    )
}