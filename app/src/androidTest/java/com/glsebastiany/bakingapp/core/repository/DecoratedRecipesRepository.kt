package com.glsebastiany.bakingapp.core.repository

import android.content.Context
import com.glsebastiany.bakingapp.core.SimpleIdlingResource
import com.glsebastiany.bakingapp.repository.RecipesRepository
import com.glsebastiany.bakingapp.repository.model.Recipe
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class DecoratedRecipesRepository(context: Context) : RecipesRepository(context) {
    val idlingResource = SimpleIdlingResource()

    override fun getRecipes(): Single<List<Recipe>> {
        return super
                .getRecipes()
                .delay(2, TimeUnit.SECONDS) // force a delay to make use of IdlingResource necessary
                .doOnSubscribe {
                    println("ois")
                    idlingResource.setIdleState(false)
                }
                .doAfterTerminate { idlingResource.setIdleState(true) }
    }
}
