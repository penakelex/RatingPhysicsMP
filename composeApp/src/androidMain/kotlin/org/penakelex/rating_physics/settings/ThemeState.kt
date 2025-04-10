package org.penakelex.rating_physics.settings

enum class ThemeState() {
    Light,
    Dark,
    System,
}

fun String?.toThemeState(): ThemeState =
    this?.let { ThemeState.valueOf(this) } ?: ThemeState.System
