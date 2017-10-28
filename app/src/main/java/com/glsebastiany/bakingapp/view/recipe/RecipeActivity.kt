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

        if (intent.hasExtra(EXTRA_RECIPE)) {
            recipe = Parcels.unwrap<Recipe>(intent.getParcelableExtra<Parcelable>(EXTRA_RECIPE))
        } else {
            throw RuntimeException(this.toString() + " must be created with " + EXTRA_RECIPE)
        }

        binding.obj = recipe
        profileViewModel.setRecipe(recipe)

        if (savedInstanceState == null) {
            addStartingFragments()
        }
    }

    private fun addStartingFragments() {
        val listFragment = RecipeStepsListFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_list, listFragment)
                .commit()

        if (binding is ActivityRecipeDetailBindingSw600dpImpl) {
            val detailFragment = RecipeIngredientsFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container_detail, detailFragment)
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

        fun startActivity(context: Context, recipe: Recipe) {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra(EXTRA_RECIPE, Parcels.wrap<Recipe>(recipe))

            context.startActivity(intent)
        }

    }

}