package org.penakelex.rating_physics.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.penakelex.rating_physics.data.repository.RatingRepositoryImplementation
import org.penakelex.rating_physics.domain.repository.RatingRepository
import org.penakelex.rating_physics.domain.use_case.GetLatestApplicationVersionUseCase
import org.penakelex.rating_physics.domain.use_case.GetRatingDataUseCase
import org.penakelex.rating_physics.domain.use_case.RatingUseCases
import org.penakelex.rating_physics.enter.EnterStateHolder
import org.penakelex.rating_physics.information.ApplicationInformationStateHolder
import org.penakelex.rating_physics.rating.RatingStateHolder

val dataModule = module {
    singleOf(::RatingRepositoryImplementation) {
        bind<RatingRepository>()
    }
}

val domainModule = module {
    single {
        val ratingRepository = get<RatingRepository>()
        RatingUseCases(
            getRatingData = GetRatingDataUseCase(ratingRepository),
            getLatestApplicationVersion = GetLatestApplicationVersionUseCase(ratingRepository)
        )
    }
}

val appModule = module {
    factoryOf(::EnterStateHolder)
    factoryOf(::RatingStateHolder)
    factoryOf(::ApplicationInformationStateHolder)
}