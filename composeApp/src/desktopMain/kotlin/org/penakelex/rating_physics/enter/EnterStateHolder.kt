package org.penakelex.rating_physics.enter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class EnterStateHolder() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _password = mutableStateOf(PasswordState())
    val password: State<PasswordState> = _password

    private val _file = mutableStateOf(FileState())
    val file: State<FileState> = _file

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: EnterEvent) {
        when (event) {
            is EnterEvent.EnteredPassword -> {
                val enteredPassword = event.password

                val passwordValue =
                    if (enteredPassword.lastOrNull() != '\n') enteredPassword
                    else password.value.value

                _password.value = password.value.copy(
                    value = passwordValue,
                    isCorrect = enteredPassword.length <= 5
                            && enteredPassword.matches(Regex("\\d+"))
                )
            }

            is EnterEvent.FileSelected -> {
                _file.value = file.value.copy(
                    path = event.filePath
                )
            }

            is EnterEvent.ValidateData -> {
                val password = password.value
                val file = file.value

                var isPasswordCorrect = true
                var isFileValid = true

                if (password.value.length !in 4..5
                    || !password.value.matches(Regex("\\d+"))
                ) {
                    _password.value = password.copy(isCorrect = false)
                    isPasswordCorrect = false
                }

                if (file.path.isBlank() || !Files.exists(Paths.get(file.path))) {
                    _file.value = file.copy(isValid = false)
                    isFileValid = false
                }

                if (!isPasswordCorrect || !isFileValid)
                    return

                scope.launch(Dispatchers.IO) {
                    try {
                        _eventFlow.emit(UIEvent.ValidateData(password.value.toUInt(), file.path))
                    } catch (exception: IOException) {
                        exception.printStackTrace()
                    }
                }
            }
        }
    }

    fun cancel() = scope.cancel()

    sealed class UIEvent {
        data class ValidateData(val password: UInt, val filePath: String) : UIEvent()
    }
}
