package org.penakelex.rating_physics.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PracticalLesson(
    @SerialName("not_attend") val notAttend: Boolean,
    val tasks: UByte?,
)