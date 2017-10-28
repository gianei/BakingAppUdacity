package com.glsebastiany.bakingapp.injection.module

import android.content.Context
import com.glsebastiany.bakingapp.MyApplication
import com.glsebastiany.bakingapp.injection.scopes.ApplicationContext
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule(private val application: MyApplication) {

    @Provides
    @ApplicationContext
    fun providesApplication(): MyApplication = application

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context = application

}
