package com.glsebastiany.bakingapp.view.recipe;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.glsebastiany.bakingapp.repository.model.Recipe;

public class RecipeViewModel extends AndroidViewModel {

    private int stepIndex = 0;
    private MutableLiveData<Recipe> recipe = new MutableLiveData<>();

    public RecipeViewModel(Application application) {
        super(application);
    }

    public void decreaseStepIndex() {
        stepIndex--;

        if (stepIndex == 0) {
            stepIndex = 0;
        }
    }

    public void increaseStepIndex() {
        stepIndex++;

        //noinspection ConstantConditions
        if (stepsNotNull() && recipe.getValue().getSteps().size() == stepIndex) {
            stepIndex = 0;
        }
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    public int getStepIndex() {
        return stepIndex;
    }

    public boolean canIncreaseStep() {
        if (stepsNotNull()) {
            //noinspection ConstantConditions
            return stepIndex < recipe.getValue().getSteps().size() - 1;
        }
        return false;
    }

    private boolean stepsNotNull() {
        return recipe != null &&
                recipe.getValue() != null &&
                recipe.getValue().getSteps() != null;
    }

    public LiveData<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe.setValue(recipe);
    }

}