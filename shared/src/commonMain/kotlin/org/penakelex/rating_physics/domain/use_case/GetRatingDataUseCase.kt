package org.penakelex.rating_physics.domain.use_case

import org.penakelex.rating_physics.data.repository.InvalidPasswordException
import org.penakelex.rating_physics.domain.model.RatingData
import org.penakelex.rating_physics.domain.repository.RatingRepository

class GetRatingDataUseCase(private val repository: RatingRepository) {
    @Throws(InvalidPasswordException::class)
    suspend operator fun invoke(password: UInt, fileBytes: ByteArray): RatingData =
        repository.getRatingDataByPassword(password, fileBytes)
}