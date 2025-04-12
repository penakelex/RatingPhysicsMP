package org.penakelex.rating_physics.rating

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.penakelex.rating_physics.data.repository.CanNotAccessServerException
import org.penakelex.rating_physics.data.repository.InvalidPasswordException
import org.penakelex.rating_physics.domain.model.RatingData
import org.penakelex.rating_physics.domain.use_case.RatingUseCases
import java.io.File

class RatingStateHolder(
    private val useCases: RatingUseCases,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var ratingData: RatingData
        private set

    private val _data = mutableStateOf(DataState.NotYetSearched)
    val data: State<DataState> = _data

    fun onEvent(event: RatingEvent) {
        when (event) {
            is RatingEvent.RequestedDataSearch -> {
                _data.value = DataState.LoadingData

                scope.launch {
                    try {
                        ratingData = useCases.getRatingData(
                            event.password,
                            File(event.filePath).readBytes(),
                        )

                        _data.value = DataState.LoadedData
                    } catch (exception: CanNotAccessServerException) {
                        _data.value = DataState.CantAccessServer
                        exception.printStackTrace()
                    } catch (exception: InvalidPasswordException) {
                        _data.value = DataState.NoLoadedData
                        exception.printStackTrace()
                    }
                }
            }
        }
    }

    fun cancel() = scope.cancel()
}