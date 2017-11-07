package com.glsebastiany.bakingapp.view.recipe.ingredient

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.databinding.FragmentIngredientsBinding
import com.glsebastiany.bakingapp.view.recipe.RecipeViewModel

class RecipeIngredientsFragment : Fragment() {

    private var fragmentListener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentIngredientsBinding

    private lateinit var recipeViewModel: RecipeViewModel

    private var listAdapter: RecipeIngredientsAdapter = RecipeIngredientsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false)

        binding.rvIngredients.adapter = listAdapter
        binding.rvIngredients.layoutManager = LinearLayoutManager(context)

        binding.fragment = this

        recipeViewModel = ViewModelProviders.of(activity!!).get(RecipeViewModel::class.java)

        recipeViewModel.getRecipe().observe(this, Observer { recipe ->
            listAdapter.updateItems(recipe?.ingredients)
        })
        return binding.root
    }

    fun onNextClick(view: View){
        fragmentListener?.onIngredientNextClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            fragmentListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
    }

    interface OnFragmentInteractionListener {
        fun onIngredientNextClick()
    }

    companion object {

        fun newInstance(): RecipeIngredientsFragment {
            return RecipeIngredientsFragment()
        }

    }
}