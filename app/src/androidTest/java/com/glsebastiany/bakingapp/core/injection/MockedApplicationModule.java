package com.glsebastiany.bakingapp.core.injection;

import com.glsebastiany.bakingapp.MyApplication;
import com.glsebastiany.bakingapp.core.repository.DecoratedRecipesRepository;
import com.glsebastiany.bakingapp.injection.module.ApplicationModule;
import com.glsebastiany.bakingapp.injection.scopes.ApplicationContext;
import com.glsebastiany.bakingapp.repository.RecipesRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class MockedApplicationModule extends ApplicationModule {

     private MyApplication application;

     public MockedApplicationModule(MyApplication application){
         super(application);
         this.application = application;
        }

    @Provides
    @ApplicationContext
    public RecipesRepository provideRecipeRepository() {
        return new DecoratedRecipesRepository(application);
    }

}