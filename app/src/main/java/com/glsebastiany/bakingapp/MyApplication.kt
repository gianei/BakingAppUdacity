package com.glsebastiany.bakingapp

import android.app.Application
import com.glsebastiany.bakingapp.injection.component.ApplicationComponent
import com.glsebastiany.bakingapp.injection.component.DaggerApplicationComponent
import com.glsebastiany.bakingapp.injection.module.ApplicationModule
import timber.log.Timber
import timber.log.Timber.DebugTree


class MyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()

    }
}