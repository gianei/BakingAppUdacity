package com.glsebastiany.bakingapp.core.injection

import com.glsebastiany.bakingapp.MyApplication
import com.glsebastiany.bakingapp.core.repository.DecoratedRecipesRepository
import com.glsebastiany.bakingapp.injection.module.ApplicationModule
import com.glsebastiany.bakingapp.injection.scopes.ApplicationContext
import com.glsebastiany.bakingapp.repository.RecipesRepository
import dagger.Module
import dagger.Provides

@Module
class MockedApplicationModule(private val application: MyApplication) : ApplicationModule(application) {

    @Provides
    @ApplicationContext
    override fun provideRecipeRepository(): RecipesRepository = DecoratedRecipesRepository(application)

}