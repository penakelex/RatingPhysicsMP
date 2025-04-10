package org.penakelex.rating_physics.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.penakelex.rating_physics.settings.util.getStringPreference
import org.penakelex.rating_physics.settings.util.savePreference

class SettingsViewModel(private val context: Application) : ViewModel() {
    companion object {
        const val THEME_KEY = "Theme"
    }

    var isThemeLoaded = false
        private set

    private val _themeState = MutableStateFlow<ThemeState>(ThemeState.Light)
    val themeState = _themeState.asStateFlow()

    init {
        viewModelScope.launch {
            _themeState.value = getStringPreference(context, THEME_KEY).toThemeState()
            isThemeLoaded = true
        }
    }

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