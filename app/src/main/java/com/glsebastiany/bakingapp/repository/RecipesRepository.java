package com.glsebastiany.bakingapp.repository;

import android.content.Context;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.repository.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RecipesRepository {

    private Context context;

    @Inject
    public RecipesRepository(Context context) {
        this.context = context;
    }

    public Single<List<Recipe>> getRecipes() {

        return Single.create(subscriber -> {

            InputStream rawJson = context.getResources().openRawResource(R.raw.baking);
            BufferedReader reader = new BufferedReader(new InputStreamReader(rawJson));

            List<Recipe> recipes = new Gson().fromJson(
                    reader,
                    new TypeToken<List<Recipe>>() {
                    }.getType());

            subscriber.onSuccess(recipes);

        });
    }

}