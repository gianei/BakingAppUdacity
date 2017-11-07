package com.glsebastiany.bakingapp.view.recipe.list

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
import com.glsebastiany.bakingapp.databinding.FragmentRecipeStepsListBinding
import com.glsebastiany.bakingapp.repository.model.Step
import com.glsebastiany.bakingapp.view.recipe.RecipeViewModel
import com.glsebastiany.bakingapp.view.recyclerview.RVEventHandler

class RecipeStepsListFragment : Fragment() {

    private var fragmentListener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentRecipeStepsListBinding

    private lateinit var profileViewModel: RecipeViewModel
    private lateinit var listAdapter: RecipeStepsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps_list, container, false)
        listAdapter = RecipeStepsListAdapter(
                RVEventHandler { _, step -> fragmentListener?.onStepClick(step) })

        binding.fragment = this

        binding.rvRecipesSteps.adapter = listAdapter
        binding.rvRecipesSteps.layoutManager = LinearLayoutManager(context)

        profileViewModel = ViewModelProviders.of(activity!!).get(RecipeViewModel::class.java)

        profileViewModel.getRecipe().observe(this, Observer { recipe ->
            listAdapter.updateItems(recipe?.steps)
        })

        return binding.root
    }

    fun onIngredientsClick(view: View) {
        fragmentListener?.onIngredientsClick()
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
        fun onStepClick(step: Step)
        fun onIngredientsClick()
    }

    companion object {

        fun newInstance(): RecipeStepsListFragment {
            return RecipeStepsListFragment()
        }

    }
}
