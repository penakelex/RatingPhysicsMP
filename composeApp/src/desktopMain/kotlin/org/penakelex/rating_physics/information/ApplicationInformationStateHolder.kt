package org.penakelex.rating_physics.information

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.penakelex.rating_physics.data.repository.CanNotAccessServerException
import org.penakelex.rating_physics.domain.repository.Platform
import org.penakelex.rating_physics.domain.use_case.RatingUseCases

class ApplicationInformationStateHolder(
    private val useCases: RatingUseCases,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _latestVersion: MutableState<String?> = mutableStateOf(null)
    val latestVersion: State<String?> = _latestVersion

    init {
        scope.launch {
            try {
                _latestVersion.value = useCases.getLatestApplicationVersion(Platform.Desktop)
            } catch (exception: CanNotAccessServerException) {
                exception.printStackTrace()
            }
        }
    }

    fun cancel() {
        scope.cancel()
    }
}