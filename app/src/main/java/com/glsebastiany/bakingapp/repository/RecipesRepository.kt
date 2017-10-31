package com.glsebastiany.bakingapp.repository

import android.content.Context
import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.repository.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

open class RecipesRepository @Inject
constructor(private val context: Context) {

    open fun getRecipes(): Single<List<Recipe>> {

        return Single.create<List<Recipe>> { subscriber ->

            val rawJson = context.resources.openRawResource(R.raw.baking)
            val reader = BufferedReader(InputStreamReader(rawJson))

            val recipes: List<Recipe> = Gson().fromJson(
                    reader,
                    object : TypeToken<List<Recipe>>() {}.type)

            subscriber.onSuccess(recipes)

        }
    }

}