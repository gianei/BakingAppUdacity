package com.glsebastiany.bakingapp.view.main;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.databinding.ActivityMainBinding;
import com.glsebastiany.bakingapp.repository.RecipesRepository;
import com.glsebastiany.bakingapp.repository.model.Recipe;
import com.glsebastiany.bakingapp.util.Util;
import com.glsebastiany.bakingapp.view.recipe.RecipeActivity;
import com.glsebastiany.bakingapp.widget.BakingAppWidgetProvider;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    @Inject
    RecipesRepository recipesRepository;
    private ActivityMainBinding binding;
    private MainActivityViewModel mainActivityViewModel;
    private MainActivityAdapter adapter;
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Intent widgetConfigResultIntent = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Util.getApplicationComponent(this).inject(this);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        checkForWidgetIntents();

        new MainActivityAdapter((view, recipe) -> {

        });


        adapter = new MainActivityAdapter((view, recipe) -> {
            if (widgetConfigResultIntent != null) {
                configureWidget(recipe);
            } else {
                RecipeActivity.startActivity(view.getContext(), recipe);
            }
        });

        binding.rvRecipes.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.recipes_grid_span));
        binding.rvRecipes.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecorationH = new DividerItemDecoration(binding.rvRecipes.getContext(), DividerItemDecoration.HORIZONTAL);
        DividerItemDecoration dividerItemDecorationV = new DividerItemDecoration(binding.rvRecipes.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecorationH.setDrawable(getResources().getDrawable(R.drawable.recycler_view_divider));
        dividerItemDecorationV.setDrawable(getResources().getDrawable(R.drawable.recycler_view_divider));
        binding.rvRecipes.addItemDecoration(dividerItemDecorationH);
        binding.rvRecipes.addItemDecoration(dividerItemDecorationV);


        mainActivityViewModel.getRecipes().observe(this, recipes -> {
            adapter.updateItems(recipes);
        });

    }

    private void configureWidget(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putInt(BakingAppWidgetProvider.EXTRA_RECIPE_INDEX, adapter.indexOf(recipe));

        appWidgetManager.updateAppWidgetOptions(mAppWidgetId, bundle);

        BakingAppWidgetProvider.updateAppWidget(this, appWidgetManager, mAppWidgetId, recipesRepository);

        setResult(Activity.RESULT_OK, widgetConfigResultIntent);
        finish();
    }

    private void checkForWidgetIntents() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
                mAppWidgetId = extras.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                widgetConfigResultIntent = new Intent();
                widgetConfigResultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(Activity.RESULT_CANCELED, widgetConfigResultIntent);
            }
        }
    }

}
