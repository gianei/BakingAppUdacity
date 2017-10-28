package com.glsebastiany.bakingapp.view.recipe.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.databinding.FragmentRecipeStepDetailBinding
import com.glsebastiany.bakingapp.view.recipe.RecipeViewModel

class RecipeStepDetailFragment : Fragment() {

    private var fragmentListener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentRecipeStepDetailBinding

    lateinit var recipeViewModel: RecipeViewModel
        private set

    private var argStepIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            argStepIndex = arguments.getInt(ARG_STEP_INDEX, 0)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step_detail, container, false)

        recipeViewModel = ViewModelProviders.of(activity).get(RecipeViewModel::class.java)

        binding.fragment = this

        recipeViewModel.getRecipe().observe(this, Observer { recipe ->
            recipe?.steps?.let { steps ->
                binding.obj = steps[argStepIndex]
            }
        })

        return binding.root
    }

    fun onNextClicked(view: View) {
        fragmentListener?.onNextClick()
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
        fun onNextClick()
    }

    companion object {

        private val ARG_STEP_INDEX = "step_index"

        fun newInstance(stepIndex: Int): RecipeStepDetailFragment {
            val fragment = RecipeStepDetailFragment()
            val args = Bundle()
            args.putInt(ARG_STEP_INDEX, stepIndex)
            fragment.arguments = args
            return fragment
        }
    }

}
