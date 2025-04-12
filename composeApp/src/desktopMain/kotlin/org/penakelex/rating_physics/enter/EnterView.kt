package org.penakelex.rating_physics.enter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.penakelex.rating_physics.enter.components.FileInputField
import org.penakelex.rating_physics.enter.components.FileSelectButton
import org.penakelex.rating_physics.enter.components.PasswordInputField
import org.penakelex.rating_physics.enter.components.SearchRatingButton


@Composable
fun rememberEventStateHolder(): EnterStateHolder {
    val stateHolder = koinInject<EnterStateHolder>()

    val state = remember {
        stateHolder
    }

    DisposableEffect(Unit) {
        onDispose {
            state.cancel()
        }
    }

    return state
}

@Composable
fun EnterView(
    onDataValidateCallback: (UInt, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val stateHolder = rememberEventStateHolder()

    LaunchedEffect(Unit) {
        stateHolder.eventFlow.collectLatest { event ->
            when (event) {
                is EnterStateHolder.UIEvent.ValidateData -> {
                    onDataValidateCallback(event.password, event.filePath)
                }
            }
        }
    }

    val password = stateHolder.password.value
    val file = stateHolder.file.value

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PasswordInputField(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Top),
            password = password.value,
            onValueChange = {
                stateHolder.onEvent(EnterEvent.EnteredPassword(it))
            },
            isError = !password.isCorrect,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Top),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            FileInputField(
                modifier = Modifier.fillMaxWidth(),
                filePath = file.path,
                onValueChange = {
                    stateHolder.onEvent(EnterEvent.FileSelected(it))
                },
                isError = !file.isValid,
            )

            FileSelectButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        val fileKit = FileKit.openFilePicker(
                            type = FileKitType.File("bin")
                        )

                        fileKit?.file?.let {
                            stateHolder.onEvent(EnterEvent.FileSelected(it.path))
                        }
                    }
                },
            )
        }

        SearchRatingButton(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Bottom),
            onClick = {
                stateHolder.onEvent(EnterEvent.ValidateData)
            }
        )
    }
}