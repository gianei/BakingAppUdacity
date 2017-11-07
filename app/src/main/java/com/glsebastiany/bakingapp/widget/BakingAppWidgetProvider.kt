package com.glsebastiany.bakingapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.repository.RecipesRepository
import com.glsebastiany.bakingapp.util.Util
import com.glsebastiany.bakingapp.view.recipe.RecipeActivity
import javax.inject.Inject

class BakingAppWidgetProvider : AppWidgetProvider() {

    @Inject lateinit var recipesRepository: RecipesRepository

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        Util.getApplicationComponent(context).inject(this)

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipesRepository)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        val EXTRA_RECIPE_INDEX = "widget_recipe_index"


        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                            appWidgetId: Int, recipesRepository: RecipesRepository) {

            val options = appWidgetManager.getAppWidgetOptions(appWidgetId)

            val recipeIndex = options.getInt(EXTRA_RECIPE_INDEX, 0)

            val views = RemoteViews(context.packageName, R.layout.widget_list_view)

            setupCollectionAdapter(context, appWidgetId, recipeIndex, views)

            setupClickIntent(context, views)

            views.setEmptyView(R.id.lv_ingredients, R.id.empty_view);

            setTitleText(recipesRepository, recipeIndex, context, views)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun setTitleText(recipesRepository: RecipesRepository, recipeIndex: Int, context: Context, views: RemoteViews) {
            val recipe = recipesRepository
                    .getRecipes()
                    .blockingGet()[recipeIndex]

            val widgetText = recipe.name + " " + context.getString(R.string.ingredients)
            views.setTextViewText(R.id.tv_ingredients_title, widgetText)
        }

        private fun setupClickIntent(context: Context, views: RemoteViews) {
            val appIntent = Intent(context, RecipeActivity::class.java)
            val appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.lv_ingredients, appPendingIntent)
        }

        private fun setupCollectionAdapter(context: Context, appWidgetId: Int, recipeIndex: Int, views: RemoteViews) {
            val intent = Intent(context, ListWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.putExtra(EXTRA_RECIPE_INDEX, recipeIndex)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            views.setRemoteAdapter(R.id.lv_ingredients, intent)
        }
    }
}

