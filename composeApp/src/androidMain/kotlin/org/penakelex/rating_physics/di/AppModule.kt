package org.penakelex.rating_physics.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.penakelex.rating_physics.data.repository.RatingRepositoryImplementation
import org.penakelex.rating_physics.domain.repository.RatingRepository
import org.penakelex.rating_physics.domain.use_case.GetRatingDataUseCase
import org.penakelex.rating_physics.domain.use_case.RatingUseCases
import org.penakelex.rating_physics.enter.EnterViewModel
import org.penakelex.rating_physics.rating.RatingDataViewModel

val appModule = module {
    singleOf(::RatingRepositoryImplementation) { bind<RatingRepository>() }
    single {
        val ratingRepository = get<RatingRepository>()
        RatingUseCases(
            getRatingData = GetRatingDataUseCase(ratingRepository)
        )
    }
    viewModelOf(::EnterViewModel)
    viewModelOf(::RatingDataViewModel)
}
