package com.glsebastiany.bakingapp.view.recipe.ingredient;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.repository.model.Ingredient;
import com.glsebastiany.bakingapp.view.recyclerview.RVListBaseAdapter;

class RecipeIngredientsAdapter extends RVListBaseAdapter<Ingredient> {

    @Override
    public int getLayoutIdForPosition(int position) {
        return R.layout.list_item_ingredient;
    }

}