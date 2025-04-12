package org.penakelex.rating_physics.settings

enum class ThemeState() {
    Light,
    Dark,
    System,
}

fun ThemeState.isDarkTheme(isSystemInDarkTheme: Boolean) = when(this) {
    ThemeState.Dark -> true
    ThemeState.Light -> false
    else -> isSystemInDarkTheme
}

fun String?.toThemeState(): ThemeState =
    this?.let { ThemeState.valueOf(this) } ?: ThemeState.System
