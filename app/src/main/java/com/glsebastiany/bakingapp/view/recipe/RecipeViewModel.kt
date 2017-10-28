package com.glsebastiany.bakingapp.view.recipe

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.glsebastiany.bakingapp.repository.model.Recipe

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    var stepIndex: Int = 0

    fun increaseStepIndex(){
        stepIndex++

        if (stepIndex == recipe.value?.steps?.size) {
            stepIndex = 0
        }
    }

    fun canIncreaseStep(): Boolean {
        recipe.value?.steps?.size?.let { size ->
            return stepIndex < size - 1
        }
        return false
    }

    private val recipe: MutableLiveData<Recipe> =
            MutableLiveData()


    fun getRecipe(): LiveData<Recipe> = recipe

    fun setRecipe(recipe: Recipe) {
        this.recipe.value = recipe
    }

}