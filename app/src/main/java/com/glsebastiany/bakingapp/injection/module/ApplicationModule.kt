package com.glsebastiany.bakingapp.injection.module

import android.content.Context
import com.glsebastiany.bakingapp.MyApplication
import com.glsebastiany.bakingapp.injection.scopes.ApplicationContext
import com.glsebastiany.bakingapp.repository.RecipesRepository
import dagger.Module
import dagger.Provides


@Module
open class ApplicationModule(private val application: MyApplication) {

    @Provides
    @ApplicationContext
    fun providesApplication(): MyApplication = application

    @Provides
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    @ApplicationContext
    open fun provideRecipeRepository(): RecipesRepository = RecipesRepository(application)

}
