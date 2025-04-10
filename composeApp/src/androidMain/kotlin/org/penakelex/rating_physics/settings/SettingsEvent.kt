package org.penakelex.rating_physics.settings

sealed class SettingsEvent {
    data class ThemeChanged(val theme: ThemeState) : SettingsEvent()
}