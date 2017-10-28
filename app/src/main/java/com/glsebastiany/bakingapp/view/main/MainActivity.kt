package com.glsebastiany.bakingapp.view.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.databinding.ActivityMainBinding
import com.glsebastiany.bakingapp.view.recipe.RecipeActivity
import com.glsebastiany.bakingapp.view.recyclerview.RVEventHandler


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var adapter: MainActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        adapter = MainActivityAdapter(
                RVEventHandler { view, recipe ->
                    RecipeActivity.startActivity(view.context, recipe)
                })

        binding.rvRecipes.adapter = adapter
        binding.rvRecipes.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.recipes_grid_span))


        mainActivityViewModel.getRecipes().observe(this, Observer { recipes ->
            adapter.updateItems(recipes)
        })

    }

}