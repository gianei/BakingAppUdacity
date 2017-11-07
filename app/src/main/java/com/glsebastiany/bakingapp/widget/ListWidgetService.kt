package com.glsebastiany.bakingapp.widget


import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.repository.RecipesRepository
import com.glsebastiany.bakingapp.repository.model.Recipe
import com.glsebastiany.bakingapp.util.Util
import com.glsebastiany.bakingapp.view.recipe.RecipeActivity
import javax.inject.Inject


class ListWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {

        return ListRemoteViewsFactory(this.applicationContext,
                intent.getIntExtra(BakingAppWidgetProvider.EXTRA_RECIPE_INDEX, 0))
    }
}

class ListRemoteViewsFactory(private var context: Context, private val recipeIndex: Int)
    : RemoteViewsService.RemoteViewsFactory {

    private var recipe: Recipe? = null
    @Inject lateinit var recipesRepository: RecipesRepository

    override fun onCreate() {
        Util.getApplicationComponent(context).inject(this)
    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    override fun onDataSetChanged() {

        recipe = recipesRepository
                .getRecipes()
                .blockingGet()[recipeIndex]
    }

    override fun onDestroy() {}

    override fun getCount(): Int = recipe?.ingredients?.size ?: 0

    override fun getViewAt(position: Int): RemoteViews? {
        recipe?.ingredients?.get(position)?.let { ingredient ->

            val views = RemoteViews(context.packageName, R.layout.list_item_widget)

            views.setTextViewText(R.id.tv_description, ingredient.ingredient)

            views.setOnClickFillInIntent(R.id.tv_description, RecipeActivity.getFillInIntent(recipe!!))

            return views
        }

        return null
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = i.toLong()

    override fun hasStableIds(): Boolean = true
}

