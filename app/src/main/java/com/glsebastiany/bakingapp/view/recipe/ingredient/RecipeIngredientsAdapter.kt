package com.glsebastiany.bakingapp.view.recipe.ingredient

import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.repository.model.Ingredient
import com.glsebastiany.bakingapp.view.recyclerview.RVListBaseAdapter

class RecipeIngredientsAdapter : RVListBaseAdapter<Ingredient>() {

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.list_item_ingredient

}