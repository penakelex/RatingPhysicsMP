package org.penakelex.rating_physics.rating

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.penakelex.rating_physics.data.repository.CanNotAccessServerException
import org.penakelex.rating_physics.data.repository.InvalidPasswordException
import org.penakelex.rating_physics.domain.model.RatingData
import org.penakelex.rating_physics.domain.repository.CipheredFileType
import org.penakelex.rating_physics.domain.use_case.RatingUseCases
import java.io.File

class RatingDataViewModel(
    private val ratingUseCases: RatingUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    lateinit var ratingData: RatingData
        private set

    private val _data = mutableStateOf(DataState.LoadingData)
    val data: State<DataState> = _data

    init {
        val password = savedStateHandle.get<Int>("password")?.toUInt()
        val filePath = savedStateHandle.get<String>("path")
        val fileType = savedStateHandle.get<String>("type")?.let { CipheredFileType.valueOf(it) }

        if (password == null || filePath == null || fileType == null) {
            _data.value = DataState.NoLoadedData
        } else {
            this.viewModelScope.launch(Dispatchers.IO) {
                try {
                    ratingData =
                        ratingUseCases.getRatingData(password, File(filePath).readBytes(), fileType)
                    _data.value = DataState.LoadedData
                } catch (exception: InvalidPasswordException) {
                    exception.printStackTrace()
                    _data.value = DataState.NoLoadedData
                } catch (exception: CanNotAccessServerException) {
                    exception.printStackTrace()
                    _data.value = DataState.CantAccessServer
                }
            }
        }
    }
}