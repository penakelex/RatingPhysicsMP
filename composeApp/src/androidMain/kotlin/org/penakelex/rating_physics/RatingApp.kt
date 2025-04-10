package org.penakelex.rating_physics

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.penakelex.rating_physics.di.appModule
import org.penakelex.rating_physics.di.dataModule
import org.penakelex.rating_physics.di.domainModule

class RatingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RatingApp)
            modules(
                dataModule,
                domainModule,
                appModule,
            )
        }
    }
}

