package com.glsebastiany.bakingapp.view.main

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.databinding.ActivityMainBinding
import com.glsebastiany.bakingapp.repository.RecipesRepository
import com.glsebastiany.bakingapp.repository.model.Recipe
import com.glsebastiany.bakingapp.util.Util
import com.glsebastiany.bakingapp.view.recipe.RecipeActivity
import com.glsebastiany.bakingapp.view.recyclerview.RVEventHandler
import com.glsebastiany.bakingapp.widget.BakingAppWidgetProvider
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var adapter: MainActivityAdapter

    @Inject lateinit var recipesRepository: RecipesRepository

    private var mAppWidgetId : Int = AppWidgetManager.INVALID_APPWIDGET_ID
    private var widgetConfigResultIntent : Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        Util.getApplicationComponent(this).inject(this)

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)


        checkForWidgetIntents()


        adapter = MainActivityAdapter(
                RVEventHandler { view, recipe ->
                    if (widgetConfigResultIntent != null) {
                        configureWidget(recipe)
                    } else {
                        RecipeActivity.startActivity(view.context, recipe)
                    }
                })

        binding.rvRecipes.adapter = adapter

        val layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.recipes_grid_span))
        binding.rvRecipes.layoutManager = layoutManager

        val dividerItemDecorationH = DividerItemDecoration(binding.rvRecipes.context, DividerItemDecoration.HORIZONTAL)
        val dividerItemDecorationV = DividerItemDecoration(binding.rvRecipes.context, DividerItemDecoration.VERTICAL)
        dividerItemDecorationH.setDrawable(resources.getDrawable(R.drawable.recycler_view_divider))
        dividerItemDecorationV.setDrawable(resources.getDrawable(R.drawable.recycler_view_divider))
        binding.rvRecipes.addItemDecoration(dividerItemDecorationH)
        binding.rvRecipes.addItemDecoration(dividerItemDecorationV)


        mainActivityViewModel.getRecipes().observe(this, Observer { recipes ->
            adapter.updateItems(recipes)
        })

    }

    private fun configureWidget(recipe: Recipe) {
        val appWidgetManager = AppWidgetManager.getInstance(this)

        val bundle = Bundle()
        bundle.putInt(BakingAppWidgetProvider.EXTRA_RECIPE_INDEX, adapter.indexOf(recipe))

        appWidgetManager.updateAppWidgetOptions(mAppWidgetId, bundle)

        BakingAppWidgetProvider.updateAppWidget(this, appWidgetManager, mAppWidgetId, recipesRepository)

        setResult(Activity.RESULT_OK, widgetConfigResultIntent)
        finish()
    }

    private fun checkForWidgetIntents() {
        val extras = intent.extras
        if (extras != null) {
            if (extras.containsKey(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
                mAppWidgetId = extras.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID)
                widgetConfigResultIntent = Intent()
                widgetConfigResultIntent?.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
                setResult(Activity.RESULT_CANCELED, widgetConfigResultIntent)

            }
        }
    }

}
