package org.penakelex.rating_physics.rating

sealed class RatingEvent {
    data class RequestedDataSearch(val password: UInt, val filePath: String) : RatingEvent()
}