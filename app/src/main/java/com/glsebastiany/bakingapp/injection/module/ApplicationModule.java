package com.glsebastiany.bakingapp.injection.module;

import android.content.Context;

import com.glsebastiany.bakingapp.MyApplication;
import com.glsebastiany.bakingapp.injection.scopes.ApplicationContext;
import com.glsebastiany.bakingapp.repository.RecipesRepository;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    public MyApplication providesApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    public Context provideContext() {
        return application;
    }

    @Provides
    @ApplicationContext
    public RecipesRepository provideRecipeRepository() {
        return new RecipesRepository(application);
    }

}
