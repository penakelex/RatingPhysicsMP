package org.penakelex.rating_physics.enter

sealed class EnterEvent {
    data class EnteredPassword(val password: String) : EnterEvent()
    data class FileSelected(val filePath: String) : EnterEvent()
    data object ValidateData : EnterEvent()
}