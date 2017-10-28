package com.glsebastiany.bakingapp.view.main

import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.repository.model.Recipe
import com.glsebastiany.bakingapp.view.recyclerview.RVEventHandler
import com.glsebastiany.bakingapp.view.recyclerview.RVListBaseAdapter

class MainActivityAdapter(private val eventListener: RVEventHandler<Recipe>) : RVListBaseAdapter<Recipe>() {
    override fun getLayoutIdForPosition(position: Int): Int = R.layout.card_recipe

    override fun getEventHandlerObjForPosition(position: Int): Any? = eventListener
}