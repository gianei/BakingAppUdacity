package com.glsebastiany.bakingapp.view.recipe.list

import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.repository.model.Step
import com.glsebastiany.bakingapp.view.recyclerview.RVEventHandler
import com.glsebastiany.bakingapp.view.recyclerview.RVListBaseAdapter

class RecipeStepsListAdapter(private val rvEventHandler: RVEventHandler<Step>) : RVListBaseAdapter<Step>() {
    override fun getLayoutIdForPosition(position: Int): Int = R.layout.list_item_step

    override fun getEventHandlerObjForPosition(position: Int): Any? = rvEventHandler
}