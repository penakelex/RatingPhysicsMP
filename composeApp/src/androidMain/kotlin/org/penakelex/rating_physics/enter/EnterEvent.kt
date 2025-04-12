package org.penakelex.rating_physics.enter

import android.net.Uri

sealed class EnterEvent {
    data class FileSelected(val uri: Uri?) : EnterEvent()
    data class EnteredPassword(val value: String) : EnterEvent()
    data object ValidateData : EnterEvent()
}