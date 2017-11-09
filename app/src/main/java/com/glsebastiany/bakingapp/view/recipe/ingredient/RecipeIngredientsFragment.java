package com.glsebastiany.bakingapp.view.recipe.ingredient;

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
import com.glsebastiany.bakingapp.databinding.FragmentIngredientsBinding;
import com.glsebastiany.bakingapp.view.recipe.RecipeViewModel;

public class RecipeIngredientsFragment extends Fragment {

    private OnFragmentInteractionListener fragmentListener = null;

    private FragmentIngredientsBinding binding;

    private RecipeViewModel recipeViewModel;

    private RecipeIngredientsAdapter listAdapter = new RecipeIngredientsAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false);

        binding.rvIngredients.setAdapter(listAdapter);
        binding.rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.setFragment(this);

        recipeViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);

        recipeViewModel.getRecipe().observe(this, recipe -> {
            if (recipe != null) {
                listAdapter.updateItems(recipe.getIngredients());
            }
        });

        return binding.getRoot();
    }

    public void onNextClick(View view){
        if (fragmentListener != null)
            fragmentListener.onIngredientNextClick();
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
        void onIngredientNextClick();
    }

    public static RecipeIngredientsFragment newInstance() {
        return new RecipeIngredientsFragment();
    }
}