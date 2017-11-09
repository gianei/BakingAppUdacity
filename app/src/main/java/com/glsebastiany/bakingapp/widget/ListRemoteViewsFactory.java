package com.glsebastiany.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.repository.RecipesRepository;
import com.glsebastiany.bakingapp.repository.model.Ingredient;
import com.glsebastiany.bakingapp.repository.model.Recipe;
import com.glsebastiany.bakingapp.util.Util;
import com.glsebastiany.bakingapp.view.recipe.RecipeActivity;

import javax.inject.Inject;

public class ListRemoteViewsFactory
    implements RemoteViewsService.RemoteViewsFactory {


    private Context context;
    private int recipeIndex;
    public ListRemoteViewsFactory(Context context, int recipeIndex){
        this.context = context;
        this.recipeIndex = recipeIndex;
        }

    private Recipe recipe = null;
    @Inject
    RecipesRepository recipesRepository;

    @Override
    public void onCreate() {
        Util.getApplicationComponent(context).inject(this);
    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {

        recipe = recipesRepository
                .getRecipes()
                .blockingGet().get(recipeIndex);
    }

    @Override
    public void onDestroy() {}

    @Override
    public int getCount() {
        if (recipe == null || recipe.getIngredients() == null) {
            return 0;
        } else {
            return recipe.getIngredients().size();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (recipe == null || recipe.getIngredients() == null || recipe.getIngredients().get(position) == null) {
            return null;
        } else {
            Ingredient ingredient = recipe.getIngredients().get(position);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_item_widget);

            views.setTextViewText(R.id.tv_description, ingredient.getIngredient());

            views.setOnClickFillInIntent(R.id.tv_description, RecipeActivity.getFillInIntent(recipe));

            return views;
        }

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i ) {
        return (long) i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
