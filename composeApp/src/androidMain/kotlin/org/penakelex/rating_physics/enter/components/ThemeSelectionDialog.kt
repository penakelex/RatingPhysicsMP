package org.penakelex.rating_physics.enter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.penakelex.rating_physics.R
import org.penakelex.rating_physics.settings.ThemeState

@Composable
fun ThemeSelectionDialog(
    theme: ThemeState,
    onDismiss: () -> Unit,
    onAccept: (ThemeState) -> Unit,
) {
    val themes = listOf(
        ThemeState.Light to stringResource(R.string.light_theme_label),
        ThemeState.Dark to stringResource(R.string.dark_theme_label),
        ThemeState.System to stringResource(R.string.system_theme_label),
    )

    val selectingTheme = remember {
        mutableStateOf(theme)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        )
    ) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            //shadowElevation = 2.dp,
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.theme_label),
                    fontSize = 24.sp,
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn {
                    items(
                        count = themes.size,
                        key = { themes[it].first }
                    ) {
                        val (themeState, name) = themes[it]

                        Row(
                            modifier = Modifier
                                .selectable(
                                    selected = selectingTheme.value == themeState,
                                    onClick = {
                                        selectingTheme.value = themeState
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = selectingTheme.value == themeState,
                                onClick = {
                                    selectingTheme.value = themeState
                                },
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = name,
                                fontSize = 20.sp,
                            )
                        }

                        if (it != themes.lastIndex)
                            Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    Text(
                        modifier = Modifier
                            .clickable(onClick = onDismiss),
                        text = stringResource(R.string.cancel_label),
                        fontSize = 22.sp,
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        modifier = Modifier
                            .clickable {
                                onAccept(selectingTheme.value)
                            },
                        text = stringResource(R.string.accept_label),
                        fontSize = 22.sp,
                    )
                }
            }
        }
    }
}