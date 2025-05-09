package org.penakelex.rating_physics.settings

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.penakelex.rating_physics.domain.repository.Platform
import org.penakelex.rating_physics.domain.use_case.RatingUseCases
import org.penakelex.rating_physics.settings.util.getStringPreference
import org.penakelex.rating_physics.settings.util.savePreference

class SettingsViewModel(
    private val context: Application,
    useCases: RatingUseCases,
) : ViewModel() {
    companion object {
        const val THEME_KEY = "Theme"
    }

    private var isThemeLoaded = false
    private var isLatestVersionLoaded = false

    private val _themeState = mutableStateOf<ThemeState>(ThemeState.Light)
    val themeState: State<ThemeState> = _themeState

    var latestVersion: String? = null
        private set

    init {
        viewModelScope.launch {
            _themeState.value = getStringPreference(context, THEME_KEY).toThemeState()
            isThemeLoaded = true
        }

        viewModelScope.launch {
            try {
                latestVersion = useCases.getLatestApplicationVersion(Platform.Android)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            isLatestVersionLoaded = true
        }
    }

    fun isInitialDataLoaded(): Boolean =
        isThemeLoaded && isLatestVersionLoaded

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ThemeChanged -> {
                _themeState.value = event.theme

                viewModelScope.launch {
                    savePreference(context, THEME_KEY, event.theme)
                }
            }
        }
    }
}