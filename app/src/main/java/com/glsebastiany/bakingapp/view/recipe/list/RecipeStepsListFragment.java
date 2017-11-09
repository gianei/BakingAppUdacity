package com.glsebastiany.bakingapp.view.recipe.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.databinding.FragmentRecipeStepsListBinding;
import com.glsebastiany.bakingapp.repository.model.Step;
import com.glsebastiany.bakingapp.view.recipe.RecipeViewModel;
import com.glsebastiany.bakingapp.view.recyclerview.RVEventHandler;

public class RecipeStepsListFragment extends Fragment {

    private OnFragmentInteractionListener fragmentListener = null;

    private FragmentRecipeStepsListBinding binding;

    private RecipeViewModel profileViewModel;
    private RecipeStepsListAdapter listAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps_list, container, false);
        listAdapter = new RecipeStepsListAdapter(
        (RVEventHandler<Step>) (View view, Step step) -> {
            if (fragmentListener!= null) {
                fragmentListener.onStepClick(step);
            }

        });

        binding.setFragment(this);

        binding.rvRecipesSteps.setAdapter(listAdapter);
        binding.rvRecipesSteps.setLayoutManager(new LinearLayoutManager(getContext()));

        profileViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
;
        profileViewModel.getRecipe().observe(this, recipe -> {
            if (recipe != null) {
                listAdapter.updateItems(recipe.getSteps());
            }
        });

        return binding.getRoot();
    }

    public void onIngredientsClick(View view) {
        if (fragmentListener != null)
            fragmentListener.onIngredientsClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            fragmentListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onStepClick(Step step);
        void onIngredientsClick();
    }

    public static RecipeStepsListFragment newInstance() {
        return new RecipeStepsListFragment();
    }
}
