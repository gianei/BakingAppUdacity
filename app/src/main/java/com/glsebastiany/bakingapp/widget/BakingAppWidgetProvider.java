package com.glsebastiany.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.repository.RecipesRepository;
import com.glsebastiany.bakingapp.repository.model.Recipe;
import com.glsebastiany.bakingapp.util.Util;
import com.glsebastiany.bakingapp.view.recipe.RecipeActivity;

import javax.inject.Inject;

public class BakingAppWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_RECIPE_INDEX = "widget_recipe_index";

    @Inject
    RecipesRepository recipesRepository;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, RecipesRepository recipesRepository) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);

        int recipeIndex = options.getInt(EXTRA_RECIPE_INDEX, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        setupCollectionAdapter(context, appWidgetId, recipeIndex, views);

        setupClickIntent(context, views);

        views.setEmptyView(R.id.lv_ingredients, R.id.empty_view);

        setTitleText(recipesRepository, recipeIndex, context, views);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void setTitleText(RecipesRepository recipesRepository, int recipeIndex, Context context, RemoteViews views) {
        Recipe recipe = recipesRepository
                .getRecipes()
                .blockingGet().get(recipeIndex);

        String widgetText = recipe.getImage() + " " + context.getString(R.string.ingredients);
        views.setTextViewText(R.id.tv_ingredients_title, widgetText);
    }

    private static void setupClickIntent(Context context, RemoteViews views) {
        Intent appIntent = new Intent(context, RecipeActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.lv_ingredients, appPendingIntent);
    }

    private static void setupCollectionAdapter(Context context, int appWidgetId, int recipeIndex, RemoteViews views) {
        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra(EXTRA_RECIPE_INDEX, recipeIndex);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.lv_ingredients, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Util.getApplicationComponent(context).inject(this);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipesRepository);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

