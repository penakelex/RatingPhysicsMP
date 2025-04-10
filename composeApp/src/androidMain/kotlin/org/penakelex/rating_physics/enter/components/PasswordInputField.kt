package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import org.penakelex.rating_physics.R

@Composable
fun PasswordInputField(
    onValueChange: (String) -> Unit,
    isPasswordIncorrect: Boolean,
    password: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier,
        value = password,
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(R.string.password_title))
        },
        placeholder = {
            Text(text = stringResource(R.string.password_hint))
        },
        textStyle = TextStyle(
            fontSize = 24.sp,
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Number,
        ),
        isError = isPasswordIncorrect,
        maxLines = 1,
    )
}