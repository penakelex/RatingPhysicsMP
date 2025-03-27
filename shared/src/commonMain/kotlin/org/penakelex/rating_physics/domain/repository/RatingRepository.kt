package org.penakelex.rating_physics.domain.repository

import org.penakelex.rating_physics.domain.model.RatingData

interface RatingRepository {
    suspend fun getRatingDataByPassword(password: UInt, fileBytes: ByteArray): RatingData
}