package org.penakelex.rating_physics.domain.use_case

import org.penakelex.rating_physics.data.repository.CanNotAccessServerException
import org.penakelex.rating_physics.domain.repository.Platform
import org.penakelex.rating_physics.domain.repository.RatingRepository

class GetLatestApplicationVersionUseCase(private val repository: RatingRepository) {
    @Throws(CanNotAccessServerException::class)
    suspend operator fun invoke(platform: Platform): String =
        repository.getLatestVersion(platform)
}