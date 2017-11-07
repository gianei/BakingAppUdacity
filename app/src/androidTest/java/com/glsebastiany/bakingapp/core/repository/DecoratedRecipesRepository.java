package com.glsebastiany.bakingapp.core.repository;

import android.content.Context;

import com.glsebastiany.bakingapp.core.SimpleIdlingResource;
import com.glsebastiany.bakingapp.repository.RecipesRepository;
import com.glsebastiany.bakingapp.repository.model.Recipe;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class DecoratedRecipesRepository extends RecipesRepository {
    private SimpleIdlingResource idlingResource = new SimpleIdlingResource();

    public DecoratedRecipesRepository(Context context) {
        super(context);
    }

    public Single<List<Recipe>> getRecipes() {
        return super
                .getRecipes()
                .delay(2, TimeUnit.SECONDS) // force a delay to make use of IdlingResource necessary
                .doOnSubscribe(disposable -> idlingResource.setIdleState(false))
                .doAfterTerminate(() -> idlingResource.setIdleState(true));
    }

    public SimpleIdlingResource getIdlingResource() {
        return idlingResource;
    }
}
