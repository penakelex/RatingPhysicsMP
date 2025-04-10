package org.penakelex.rating_physics.enter

import android.net.Uri

data class FileState(
    val uri: Uri? = null,
    val isValid: Boolean = true,
    val name: String? = null,
)