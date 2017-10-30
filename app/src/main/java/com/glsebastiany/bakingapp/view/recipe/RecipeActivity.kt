package com.glsebastiany.bakingapp.view.recipe

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.databinding.ActivityRecipeDetailBinding
import com.glsebastiany.bakingapp.databinding.ActivityRecipeDetailBindingImpl
import com.glsebastiany.bakingapp.databinding.ActivityRecipeDetailBindingSw600dpImpl
import com.glsebastiany.bakingapp.repository.model.Recipe
import com.glsebastiany.bakingapp.repository.model.Step
import com.glsebastiany.bakingapp.view.recipe.detail.RecipeStepDetailFragment
import com.glsebastiany.bakingapp.view.recipe.ingredient.RecipeIngredientsFragment
import com.glsebastiany.bakingapp.view.recipe.list.RecipeStepsListFragment
import org.parceler.Parcels

class RecipeActivity :
        RecipeStepsListFragment.OnFragmentInteractionListener,
        RecipeStepDetailFragment.OnFragmentInteractionListener,
        RecipeIngredientsFragment.OnFragmentInteractionListener,
        AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding

    private lateinit var profileViewModel: RecipeViewModel

    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        profileViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)

        retrieveRecipe(intent)

        if (savedInstanceState == null) {
            addStartingFragments(intent.hasExtra(EXTRA_GO_TO_INGREDIENTS))
        }
    }

    private fun retrieveRecipe(intent: Intent) {
        if (intent.hasExtra(EXTRA_RECIPE)) {
            recipe = Parcels.unwrap<Recipe>(intent.getParcelableExtra<Parcelable>(EXTRA_RECIPE))
        } else {
            throw RuntimeException(this.toString() + " must be created with " + EXTRA_RECIPE)
        }

        binding.obj = recipe
        profileViewModel.setRecipe(recipe)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        retrieveRecipe(intent)

        var i = 0
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        while ( i < backStackEntryCount) {
            supportFragmentManager.popBackStack()
            i++
        }

        addStartingFragments(true)
    }

    private fun addStartingFragments(goToIngredients: Boolean) {

        val listFragment = RecipeStepsListFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_list, listFragment)
                .commit()

        if (binding is ActivityRecipeDetailBindingSw600dpImpl) {
            val detailFragment = RecipeIngredientsFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container_detail, detailFragment)
                    .commit()
        } else if (goToIngredients) {
            val detailFragment = RecipeIngredientsFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_list, detailFragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onStepClick(step: Step) {
        profileViewModel.stepIndex = recipe.steps?.indexOf(step) ?: -1

        if (profileViewModel.stepIndex >= 0) {
            val fragment = RecipeStepDetailFragment.newInstance(profileViewModel.stepIndex)
            replaceDetailFragmentAccordingToLayout(fragment)
        }
    }

    override fun onNextClick() {
        profileViewModel.increaseStepIndex()
        replaceRecipeDetailFragment()
    }

    override fun onIngredientNextClick() {
        profileViewModel.stepIndex = 0
        replaceRecipeDetailFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        profileViewModel.decreaseStepIndex()
    }

    override fun onIngredientsClick() {
        val fragment = RecipeIngredientsFragment.newInstance()
        replaceDetailFragmentAccordingToLayout(fragment)
    }

    private fun replaceRecipeDetailFragment() {
        if (profileViewModel.stepIndex >= 0) {
            val fragment = RecipeStepDetailFragment.newInstance(profileViewModel.stepIndex)
            replaceDetailFragmentAccordingToLayout(fragment)
        }
    }

    private fun replaceDetailFragmentAccordingToLayout(fragment: Fragment) {
        if (binding is ActivityRecipeDetailBindingImpl) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_list, fragment)
                    .addToBackStack(null)
                    .commit()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_detail, fragment)
                    .commit()
        }
    }

    companion object {

        private const val EXTRA_RECIPE = "extra_recipe"
        private const val EXTRA_GO_TO_INGREDIENTS = "extra_go_to_ingredients"

        fun startActivity(context: Context, recipe: Recipe) {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra(EXTRA_RECIPE, Parcels.wrap<Recipe>(recipe))

            context.startActivity(intent)
        }

        fun getFillInIntent(recipe: Recipe): Intent {
            val extras = Bundle()
            extras.putParcelable(EXTRA_RECIPE, Parcels.wrap<Recipe>(recipe))
            extras.putBoolean(EXTRA_GO_TO_INGREDIENTS, true)
            val fillInIntent = Intent()
            fillInIntent.putExtras(extras)
            return fillInIntent
        }

    }

}