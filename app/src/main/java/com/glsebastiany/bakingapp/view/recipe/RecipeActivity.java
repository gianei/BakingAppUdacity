package com.glsebastiany.bakingapp.view.recipe;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.databinding.ActivityRecipeDetailBinding;
import com.glsebastiany.bakingapp.databinding.ActivityRecipeDetailBindingImpl;
import com.glsebastiany.bakingapp.databinding.ActivityRecipeDetailBindingSw600dpImpl;
import com.glsebastiany.bakingapp.repository.model.Recipe;
import com.glsebastiany.bakingapp.repository.model.Step;
import com.glsebastiany.bakingapp.view.recipe.detail.RecipeStepDetailFragment;
import com.glsebastiany.bakingapp.view.recipe.ingredient.RecipeIngredientsFragment;
import com.glsebastiany.bakingapp.view.recipe.list.RecipeStepsListFragment;

import org.parceler.Parcels;

public class RecipeActivity extends AppCompatActivity implements
        RecipeStepsListFragment.OnFragmentInteractionListener,
        RecipeStepDetailFragment.OnFragmentInteractionListener,
        RecipeIngredientsFragment.OnFragmentInteractionListener {

    public static final String EXTRA_RECIPE = "extra_recipe";
    private static final String EXTRA_GO_TO_INGREDIENTS = "extra_go_to_ingredients";

    private ActivityRecipeDetailBinding binding;

    private RecipeViewModel profileViewModel;

    private Recipe recipe;

    public static void startActivity(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeActivity.class);
        intent.putExtra(EXTRA_RECIPE, Parcels.wrap(recipe));

        context.startActivity(intent);
    }

    public static Intent getFillInIntent(Recipe recipe) {
        Bundle extras = new Bundle();
        extras.putParcelable(EXTRA_RECIPE, Parcels.wrap(recipe));
        extras.putBoolean(EXTRA_GO_TO_INGREDIENTS, true);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        return fillInIntent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        profileViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        retrieveRecipe(getIntent());

        if (savedInstanceState == null) {
            addStartingFragments(getIntent().hasExtra(EXTRA_GO_TO_INGREDIENTS));
        }
    }

    private void retrieveRecipe(Intent intent) {
        if (intent.hasExtra(EXTRA_RECIPE)) {
            recipe = Parcels.unwrap(intent.getParcelableExtra(EXTRA_RECIPE));
        } else {
            throw new RuntimeException(this.toString() + " must be created with " + EXTRA_RECIPE);
        }

        binding.setObj(recipe);
        profileViewModel.setRecipe(recipe);
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        retrieveRecipe(intent);

        int i = 0;
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        while (i < backStackEntryCount) {
            getSupportFragmentManager().popBackStack();
            i++;
        }

        addStartingFragments(true);
    }

    private void addStartingFragments(Boolean goToIngredients) {

        Fragment listFragment = RecipeStepsListFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_list, listFragment)
                .commit();

        if (binding instanceof ActivityRecipeDetailBindingSw600dpImpl){
            Fragment detailFragment = RecipeIngredientsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_detail, detailFragment)
                    .commit();
        } else if (goToIngredients) {
            Fragment detailFragment = RecipeIngredientsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_list, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onStepClick(Step step) {
        if (recipe.getSteps() != null) {
            profileViewModel.setStepIndex(recipe.getSteps().indexOf(step));
        } else {
            profileViewModel.setStepIndex(-1);
        }

        if (profileViewModel.getStepIndex() >= 0) {
            Fragment fragment = RecipeStepDetailFragment.newInstance(profileViewModel.getStepIndex());
            replaceDetailFragmentAccordingToLayout(fragment);
        }
    }

    @Override
    public void onNextClick() {
        profileViewModel.increaseStepIndex();
        replaceRecipeDetailFragment();
    }

    @Override
    public void onIngredientNextClick() {
        profileViewModel.setStepIndex(0);
        replaceRecipeDetailFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        profileViewModel.decreaseStepIndex();
    }

    @Override
    public void onIngredientsClick() {
        Fragment fragment = RecipeIngredientsFragment.newInstance();
        replaceDetailFragmentAccordingToLayout(fragment);
    }

    private void replaceRecipeDetailFragment() {
        if (profileViewModel.getStepIndex() >= 0) {
            Fragment fragment = RecipeStepDetailFragment.newInstance(profileViewModel.getStepIndex());
            replaceDetailFragmentAccordingToLayout(fragment);
        }
    }

    private void replaceDetailFragmentAccordingToLayout(Fragment fragment) {
        if (binding instanceof ActivityRecipeDetailBindingImpl) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_list, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_detail, fragment)
                    .commit();
        }
    }

}